package org.customcache.service;

public interface Cache<K, V> {
    void cachePut(K key, V cacheObject);
    V cacheGet(K key);
    int cacheSize();
}
