package customcache.lfu;

import org.customcache.config.CacheFactory;
import org.customcache.config.CacheType;
import org.customcache.exceptions.CacheMissedException;
import org.customcache.service.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LFUTest {
    @Test
    public void createCacheExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> CacheFactory.createCache(CacheType.LFU, 0));
    }

    @Test
    public void putCacheTest() {
        Cache<Integer, String> cache = CacheFactory.createCache(CacheType.LFU, 2);
        cache.cachePut(1, "1");
        cache.cachePut(2, "2");

        //Увеличиваем количество обращений к кэшу
        cache.cacheGet(1);
        cache.cacheGet(2);
        cache.cacheGet(2);

        //при вставке нового вытесняется элемент с ключом 1 т.к. у него меньшая частота обращений
        cache.cachePut(3, "3");

        //проверяем, что метод вытесения LFU работает
        Assertions.assertEquals(cache.cacheSize(), 2);
        Assertions.assertThrows(CacheMissedException.class, () -> cache.cacheGet(1));
    }
}
