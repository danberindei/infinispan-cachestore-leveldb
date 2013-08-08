package org.infinispan.loaders.leveldb;

import java.io.File;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.LoadersConfigurationBuilder;
import org.infinispan.container.entries.InternalCacheEntry;
import org.infinispan.loaders.BaseCacheStoreTest;
import org.infinispan.loaders.CacheLoaderException;
import org.infinispan.loaders.leveldb.configuration.LevelDBCacheStoreConfiguration;
import org.infinispan.loaders.leveldb.configuration.LevelDBCacheStoreConfigurationBuilder;
import org.infinispan.loaders.spi.CacheStore;
import org.infinispan.test.TestingUtil;
import org.infinispan.test.fwk.TestInternalCacheEntryFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "loaders.leveldb.LevelDBCacheStoreTest")
public class LevelDBCacheStoreTest extends BaseCacheStoreTest {

   private LevelDBCacheStore fcs;
   private String tmpDirectory;

   @BeforeClass
   protected void setUpTempDir() {
      tmpDirectory = TestingUtil.tmpDirectory(this);
   }

   @AfterClass(alwaysRun = true)
   protected void clearTempDir() {
      TestingUtil.recursiveFileRemove(tmpDirectory);
      new File(tmpDirectory).mkdirs();
   }

   protected LevelDBCacheStoreConfiguration createCacheStoreConfig(LoadersConfigurationBuilder lcb) throws CacheLoaderException {
      LevelDBCacheStoreConfigurationBuilder cfg = new LevelDBCacheStoreConfigurationBuilder(lcb);
      cfg.location(tmpDirectory + "/data");
      cfg.expiredLocation(tmpDirectory + "/expiry");
      cfg.clearThreshold(2);
      cfg.purgeSynchronously(true); // for more accurate unit testing
      return cfg.create();
   }

   @Override
   protected CacheStore createCacheStore() throws CacheLoaderException {
      clearTempDir();
      fcs = new LevelDBCacheStore();
      ConfigurationBuilder cb = new ConfigurationBuilder();
      LevelDBCacheStoreConfiguration cfg = createCacheStoreConfig(cb.loaders());
      fcs.init(cfg, getCache(), getMarshaller());
      fcs.start();
      return fcs;
   }

   @Override
   public void testPurgeExpired() throws Exception {
      long lifespan = 1000;
      InternalCacheEntry k1 = TestInternalCacheEntryFactory.create("k1", "v1", lifespan);
      InternalCacheEntry k2 = TestInternalCacheEntryFactory.create("k2", "v2", lifespan);
      InternalCacheEntry k3 = TestInternalCacheEntryFactory.create("k3", "v3", lifespan);
      cs.store(k1);
      cs.store(k2);
      cs.store(k3);
      assert cs.containsKey("k1");
      assert cs.containsKey("k2");
      assert cs.containsKey("k3");
      Thread.sleep(lifespan + 100);
      cs.purgeExpired();
      LevelDBCacheStore fcs = (LevelDBCacheStore) cs;
      assert fcs.load("k1") == null;
      assert fcs.load("k2") == null;
      assert fcs.load("k3") == null;
   }

   public void testStopStartDoesntNukeValues() throws InterruptedException, CacheLoaderException {
      assert !cs.containsKey("k1");
      assert !cs.containsKey("k2");

      long lifespan = 1;
      long idle = 1;
      InternalCacheEntry se1 = TestInternalCacheEntryFactory.create("k1", "v1", lifespan);
      InternalCacheEntry se2 = TestInternalCacheEntryFactory.create("k2", "v2");
      InternalCacheEntry se3 = TestInternalCacheEntryFactory.create("k3", "v3", -1, idle);
      InternalCacheEntry se4 = TestInternalCacheEntryFactory.create("k4", "v4", lifespan, idle);

      cs.store(se1);
      cs.store(se2);
      cs.store(se3);
      cs.store(se4);
      Thread.sleep(100);
      // Force a purge expired so that expiry tree is updated
      cs.purgeExpired();
      cs.stop();
      cs.start();
      assert se1.isExpired();
      assert cs.load("k1") == null;
      assert !cs.containsKey("k1");
      assert cs.load("k2") != null;
      assert cs.containsKey("k2");
      assert cs.load("k2").getValue().equals("v2");
      assert se3.isExpired();
      assert cs.load("k3") == null;
      assert !cs.containsKey("k3");
      assert se3.isExpired();
      assert cs.load("k3") == null;
      assert !cs.containsKey("k3");
   }

}
