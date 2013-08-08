package org.infinispan.loaders.leveldb;

import org.infinispan.configuration.cache.LoadersConfigurationBuilder;
import org.infinispan.loaders.leveldb.configuration.LevelDBCacheStoreConfiguration;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "loaders.leveldb.JavaLevelDBCacheStoreFunctionalTest")
public class JavaLevelDBCacheStoreFunctionalTest extends LevelDBCacheStoreFunctionalTest {

   @Override
   protected LoadersConfigurationBuilder createCacheStoreConfig(LoadersConfigurationBuilder loaders) {
      super.createStoreBuilder(loaders).implementationType(LevelDBCacheStoreConfiguration.ImplementationType.JAVA);
      return loaders;
   }
}
