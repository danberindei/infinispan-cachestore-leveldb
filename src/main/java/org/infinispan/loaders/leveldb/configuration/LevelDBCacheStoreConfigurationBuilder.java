package org.infinispan.loaders.leveldb.configuration;

import org.infinispan.commons.configuration.Builder;
import org.infinispan.configuration.cache.AbstractStoreConfigurationBuilder;
import org.infinispan.configuration.cache.PersistenceConfigurationBuilder;
import org.iq80.leveldb.CompressionType;

/**
 * 
 * @author <a href="mailto:rtsang@redhat.com">Ray Tsang</a>
 * 
 */
public class LevelDBCacheStoreConfigurationBuilder extends AbstractStoreConfigurationBuilder<LevelDBCacheStoreConfiguration, LevelDBCacheStoreConfigurationBuilder> {

   protected String location = "Infinispan-LevelDBCacheStore/data";
   protected String expiredLocation = "Infinispan-LevelDBCacheStore/expired";
   protected CompressionType compressionType = CompressionType.NONE;
   protected LevelDBCacheStoreConfiguration.ImplementationType implementationType = LevelDBCacheStoreConfiguration.ImplementationType.AUTO;
   protected Integer blockSize;
   protected Long cacheSize;

   protected int expiryQueueSize = 10000;
   protected int clearThreshold = 10000;

   public LevelDBCacheStoreConfigurationBuilder(PersistenceConfigurationBuilder builder) {
      super(builder);
   }

   public LevelDBCacheStoreConfigurationBuilder location(String location) {
      this.location = location;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder expiredLocation(String expiredLocation) {
      this.expiredLocation = expiredLocation;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder implementationType(LevelDBCacheStoreConfiguration.ImplementationType implementationType) {
      this.implementationType = implementationType;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder blockSize(int blockSize) {
      this.blockSize = blockSize;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder cacheSize(long cacheSize) {
      this.cacheSize = cacheSize;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder expiryQueueSize(int expiryQueueSize) {
      this.expiryQueueSize = expiryQueueSize;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder clearThreshold(int clearThreshold) {
      this.clearThreshold = clearThreshold;
      return self();
   }

   public LevelDBCacheStoreConfigurationBuilder compressionType(CompressionType compressionType) {
      this.compressionType = compressionType;
      return self();
   }

   @Override
   public void validate() {
      // how do you validate required attributes?
      super.validate();
   }

   @Override
   public LevelDBCacheStoreConfiguration create() {
      return new LevelDBCacheStoreConfiguration(purgeOnStartup, fetchPersistentState, ignoreModifications, async.create(),
                                                singletonStore.create(), preload, shared, properties,location,
                                                expiredLocation, implementationType, compressionType,  blockSize,
                                                cacheSize, expiryQueueSize, clearThreshold);
   }

   @Override
   public Builder<?> read(LevelDBCacheStoreConfiguration template) {
      location = template.location();
      expiredLocation = template.expiredLocation();
      implementationType = template.implementationType();
      preload = template.preload();
      shared = template.shared();

      compressionType = template.compressionType();
      blockSize = template.blockSize();
      cacheSize = template.cacheSize();

      expiryQueueSize = template.expiryQueueSize();
      clearThreshold = template.clearThreshold();


      // AbstractStore-specific configuration
      fetchPersistentState = template.fetchPersistentState();
      ignoreModifications = template.ignoreModifications();
      properties = template.properties();
      purgeOnStartup = template.purgeOnStartup();
      this.async.read(template.async());
      this.singletonStore.read(template.singletonStore());

      return self();
   }

   @Override
   public LevelDBCacheStoreConfigurationBuilder self() {
      return this;
   }

}
