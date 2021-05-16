package org.customcache.service.lfu;

import org.customcache.exceptions.CacheMissedException;
import org.customcache.service.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {
    private final Map<K, LFUNode<V>> cache;
    private int maxCacheSize;

    public LFUCache(int maxCacheSize) {
        if (maxCacheSize <= 0)
            throw new IllegalArgumentException("Capacity should be more than 0");

        this.maxCacheSize = maxCacheSize;
        this.cache = new LinkedHashMap<>(maxCacheSize);
    }

    @Override
    public void cachePut(K key, V cacheObject) {
        if (cache.size() >= maxCacheSize) {
            if (!cache.containsKey(key)) {
                evictCache();
            }
        }
        cache.put(key, new LFUNode<>(cacheObject));
    }

    @Override
    public V cacheGet(K key) {
        if (!cache.containsKey(key))
            throw new CacheMissedException("The value was not found in the cache by key " + key);
        LFUNode<V> lfuNode = cache.get(key);
        lfuNode.incrementCountCalls();
        return lfuNode.getCacheValue();
    }

    private void evictCache() {
        int minCount = Integer.MAX_VALUE;
        K keyEvict = null;
        for (Map.Entry<K, LFUNode<V>> entry : cache.entrySet()) {
            LFUNode<V> lfuNode = entry.getValue();
            if (lfuNode.getCount() < minCount) {
                minCount = lfuNode.getCount();
                keyEvict = entry.getKey();
            }
        }
        cache.remove(keyEvict);
    }

    @Override
    public int cacheSize() {
        return cache.size();
    }
}
