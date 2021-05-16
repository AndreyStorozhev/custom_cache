package customcache.lru;

import org.customcache.config.CacheFactory;
import org.customcache.config.CacheType;
import org.customcache.exceptions.CacheMissedException;
import org.customcache.service.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LRUTest {

    @Test
    public void createCacheExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> CacheFactory.createCache(CacheType.LRU, 0));
    }

    @Test
    public void putCacheTest() {
        Cache<Integer, String> cache = CacheFactory.createCache(CacheType.LRU, 2);
        cache.cachePut(1, "1");
        cache.cachePut(2, "2");

        //Элемент с ключом 1 становится в конец списка приоритетов используемых элементов
        cache.cacheGet(2);

        //при вставке нового вытесняется элемент с ключом 1 т.к. он в конце списка приоритет
        cache.cachePut(3, "3");

        //проверяем, что метод вытесения LRU работает
        Assertions.assertEquals(cache.cacheSize(), 2);
        Assertions.assertThrows(CacheMissedException.class, () -> cache.cacheGet(1));
    }
}
