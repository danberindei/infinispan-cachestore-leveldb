package org.infinispan.loaders.leveldb;

import org.infinispan.configuration.cache.PersistenceConfigurationBuilder;
import org.infinispan.loaders.leveldb.configuration.LevelDBStoreConfiguration;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "loaders.leveldb.JavaLevelDBStoreFunctionalTest")
public class JavaLevelDBStoreFunctionalTest extends LevelDBStoreFunctionalTest {

   @Override
   protected PersistenceConfigurationBuilder createCacheStoreConfig(PersistenceConfigurationBuilder loaders, boolean preload) {
      super.createStoreBuilder(loaders).implementationType(LevelDBStoreConfiguration.ImplementationType.JAVA).preload(preload);
      return loaders;
   }
}
