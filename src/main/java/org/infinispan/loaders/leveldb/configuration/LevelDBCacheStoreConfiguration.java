package org.infinispan.loaders.leveldb.configuration;

import org.infinispan.commons.configuration.BuiltBy;
import org.infinispan.commons.configuration.ConfigurationFor;
import org.infinispan.commons.util.TypedProperties;
import org.infinispan.configuration.cache.AbstractLockSupportStoreConfiguration;
import org.infinispan.configuration.cache.AsyncStoreConfiguration;
import org.infinispan.configuration.cache.SingletonStoreConfiguration;
import org.infinispan.loaders.leveldb.LevelDBCacheStore;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.Options;

/**
 * 
 * @author <a href="mailto:rtsang@redhat.com">Ray Tsang</a>
 * 
 */
@ConfigurationFor(LevelDBCacheStore.class)
@BuiltBy(LevelDBCacheStoreConfigurationBuilder.class)
public class LevelDBCacheStoreConfiguration extends AbstractLockSupportStoreConfiguration {
   public enum ImplementationType {
      AUTO,
      JAVA,
      JNI
   }

   final private String location;
   final private String expiredLocation;
   final private ImplementationType implementationType;
   final private CompressionType compressionType;
   final private Integer blockSize;
   final private Long cacheSize;
   final private int expiryQueueSize;
   final private int clearThreshold;

   protected LevelDBCacheStoreConfiguration(String location, String expiredLocation, ImplementationType implementationType, CompressionType compressionType, Integer blockSize,
         Long cacheSize, int expiryQueueSize, int clearThreshold, long lockAcquistionTimeout, int lockConcurrencyLevel, boolean purgeOnStartup, boolean purgeSynchronously,
         int purgerThreads, boolean fetchPersistentState, boolean ignoreModifications, TypedProperties properties, AsyncStoreConfiguration async,
         SingletonStoreConfiguration singletonStore) {
      super(lockAcquistionTimeout, lockConcurrencyLevel, purgeOnStartup, purgeSynchronously, purgerThreads, fetchPersistentState, ignoreModifications, properties, async,
            singletonStore);

      this.location = location;
      this.expiredLocation = expiredLocation;
      this.implementationType = implementationType;
      this.compressionType = compressionType;
      this.blockSize = blockSize;
      this.cacheSize = cacheSize;
      this.expiryQueueSize = expiryQueueSize;
      this.clearThreshold = clearThreshold;
   }

   public String location() {
      return location;
   }

   public String expiredLocation() {
      return expiredLocation;
   }

   public ImplementationType implementationType() {
      return implementationType;
   }

   public CompressionType compressionType() {
      return compressionType;
   }

   public Integer blockSize() {
      return blockSize;
   }

   public Long cacheSize() {
      return cacheSize;
   }

   public int expiryQueueSize() {
      return expiryQueueSize;
   }

   public int clearThreshold() {
      return clearThreshold;
   }

   public Options dataDbOptions() {
      Options options = new Options().createIfMissing(true);

      options.compressionType(compressionType);

      if (blockSize != null) {
         options.blockSize(blockSize);
      }

      if (cacheSize != null) {
         options.cacheSize(cacheSize);
      }

      return options;
   }

   public Options expiredDbOptions() {
      return new Options().createIfMissing(true);
   }
}
