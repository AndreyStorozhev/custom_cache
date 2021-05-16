package org.customcache.service.lfu;

public class LFUNode<V> {
    private final V cacheValue;
    private int count;

    public LFUNode(V cacheValue) {
        this.cacheValue = cacheValue;
        this.count = 1;
    }

    public V getCacheValue() {
        return cacheValue;
    }

    public int getCount() {
        return count;
    }

    public void incrementCountCalls() {
        count++;
    }
}
