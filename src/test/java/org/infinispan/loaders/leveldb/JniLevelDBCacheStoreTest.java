package org.infinispan.loaders.leveldb;

import org.infinispan.configuration.cache.PersistenceConfigurationBuilder;
import org.infinispan.loaders.leveldb.configuration.LevelDBStoreConfiguration;
import org.infinispan.loaders.leveldb.configuration.LevelDBStoreConfigurationBuilder;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "loaders.leveldb.JniLevelDBCacheStoreTest")
public class JniLevelDBCacheStoreTest extends LevelDBStoreTest {

   protected LevelDBStoreConfiguration createCacheStoreConfig(PersistenceConfigurationBuilder lcb) {
      LevelDBStoreConfigurationBuilder builder = new LevelDBStoreConfigurationBuilder(lcb);
      builder.read(super.createCacheStoreConfig(lcb));
      builder.implementationType(LevelDBStoreConfiguration.ImplementationType.JNI);
      return builder.create();
   }
}
