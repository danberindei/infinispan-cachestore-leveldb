package org.infinispan.loaders.leveldb;

import java.io.File;

import org.infinispan.configuration.cache.PersistenceConfigurationBuilder;
import org.infinispan.loaders.leveldb.configuration.LevelDBStoreConfiguration;
import org.infinispan.loaders.leveldb.configuration.LevelDBStoreConfigurationBuilder;
import org.infinispan.persistence.MultiCacheStoreFunctionalTest;
import org.infinispan.test.TestingUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "loaders.leveldb.LevelDBMultiCacheStoreFunctionalTest")
public class LevelDBMultiCacheStoreFunctionalTest extends MultiCacheStoreFunctionalTest<LevelDBStoreConfigurationBuilder> {

   private File tmpDir;

   @BeforeClass
   protected void setPaths() {
      tmpDir = new File(TestingUtil.tmpDirectory(this));
   }

   @BeforeMethod
   protected void cleanDataFiles() {
      if (tmpDir.exists()) {
         TestingUtil.recursiveFileRemove(tmpDir);
      }
   }


   @Override
   protected LevelDBStoreConfigurationBuilder buildCacheStoreConfig(PersistenceConfigurationBuilder p, String discriminator) throws Exception {
      LevelDBStoreConfigurationBuilder store = p.addStore(LevelDBStoreConfigurationBuilder.class);
      store.location(tmpDir.getAbsolutePath() + File.separator + "leveldb" + File.separator + "data-" + discriminator);
      store.expiredLocation(tmpDir.getAbsolutePath() + File.separator + "leveldb" + File.separator + "expired-data-" + discriminator);
      store.implementationType(LevelDBStoreConfiguration.ImplementationType.JAVA);
      return store;
   }
}
