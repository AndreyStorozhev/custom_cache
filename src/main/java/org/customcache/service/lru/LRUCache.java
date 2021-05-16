package org.customcache.service.lru;

import org.customcache.exceptions.CacheMissedException;
import org.customcache.service.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    private final Map<K, V> cache;

    public LRUCache(int maxCacheSize) {
        if (maxCacheSize <= 0)
            throw new IllegalArgumentException("Capacity should be more than 0");

        this.cache = new LinkedHashMap<>(maxCacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxCacheSize;
            }
        };
    }

    @Override
    public void cachePut(K key, V cacheObject) {
        cache.put(key, cacheObject);
    }

    @Override
    public V cacheGet(K key) {
        if (!cache.containsKey(key))
            throw new CacheMissedException("The value was not found in the cache by key " + key);
        return cache.get(key);
    }

    @Override
    public int cacheSize() {
        return cache.size();
    }
}
