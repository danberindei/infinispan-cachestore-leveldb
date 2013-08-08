package org.infinispan.loaders.leveldb.configuration;

import org.infinispan.commons.configuration.Builder;
import org.infinispan.commons.util.TypedProperties;
import org.infinispan.configuration.cache.AbstractLockSupportStoreConfigurationBuilder;
import org.infinispan.configuration.cache.LoadersConfigurationBuilder;
import org.iq80.leveldb.CompressionType;

/**
 * 
 * @author <a href="mailto:rtsang@redhat.com">Ray Tsang</a>
 * 
 */
public class LevelDBCacheStoreConfigurationBuilder extends AbstractLockSupportStoreConfigurationBuilder<LevelDBCacheStoreConfiguration, LevelDBCacheStoreConfigurationBuilder> {

   protected String location = "Infinispan-LevelDBCacheStore/data";
   protected String expiredLocation = "Infinispan-LevelDBCacheStore/expired";
   protected CompressionType compressionType = CompressionType.NONE;
   protected LevelDBCacheStoreConfiguration.ImplementationType implementationType = LevelDBCacheStoreConfiguration.ImplementationType.AUTO;
   protected Integer blockSize;
   protected Long cacheSize;

   protected int expiryQueueSize = 10000;
   protected int clearThreshold = 10000;

   public LevelDBCacheStoreConfigurationBuilder(LoadersConfigurationBuilder builder) {
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
      return new LevelDBCacheStoreConfiguration(location, expiredLocation, implementationType, compressionType, blockSize, cacheSize, expiryQueueSize, clearThreshold,
            lockAcquistionTimeout, lockConcurrencyLevel, purgeOnStartup, purgeSynchronously, purgerThreads, fetchPersistentState, ignoreModifications,
            TypedProperties.toTypedProperties(properties), async.create(), singletonStore.create());
   }

   @Override
   public Builder<?> read(LevelDBCacheStoreConfiguration template) {
      location = template.location();
      expiredLocation = template.expiredLocation();
      implementationType = template.implementationType();

      compressionType = template.compressionType();
      blockSize = template.blockSize();
      cacheSize = template.cacheSize();

      expiryQueueSize = template.expiryQueueSize();
      clearThreshold = template.clearThreshold();

      // LockSupportStore-specific configuration
      lockAcquistionTimeout = template.lockAcquistionTimeout();
      lockConcurrencyLevel = template.lockConcurrencyLevel();

      // AbstractStore-specific configuration
      fetchPersistentState = template.fetchPersistentState();
      ignoreModifications = template.ignoreModifications();
      properties = template.properties();
      purgeOnStartup = template.purgeOnStartup();
      purgeSynchronously = template.purgeSynchronously();
      this.async.read(template.async());
      this.singletonStore.read(template.singletonStore());

      return self();
   }

   @Override
   public LevelDBCacheStoreConfigurationBuilder self() {
      return this;
   }

}
