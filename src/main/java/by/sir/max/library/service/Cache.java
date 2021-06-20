package by.sir.max.library.service;

import by.sir.max.library.exception.LibraryServiceException;

/**
 * Interface describes the behavior of Cache
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> {

    /**
     * Getting {@link V} {@link V} value from cache
     *
     * @param key {@link K} the type of keys maintained by this cache
     * @return value {@link V}  founded by key
     * @throws LibraryServiceException if {@link K} key  is null
     */
    V get(K key) throws LibraryServiceException;
}
