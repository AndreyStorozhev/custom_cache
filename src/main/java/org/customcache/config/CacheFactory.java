package org.customcache.config;

import org.customcache.service.Cache;
import org.customcache.service.lfu.LFUCache;
import org.customcache.service.lru.LRUCache;

public class CacheFactory {

    public static Cache<Integer, String> createCache(CacheType type, int maxCacheSize) {
        Cache<Integer, String> cache = null;
        switch (type) {
            case LFU:
                cache = new LFUCache<>(maxCacheSize);
                break;
            case LRU:
                cache = new LRUCache<>(maxCacheSize);
                break;
        }
        return cache;
    }
}
