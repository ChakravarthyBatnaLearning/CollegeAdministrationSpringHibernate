package com.college.student.cache.lru_ll;

import java.util.*;

public class LRUCacheWithHM<K,V>{
    private final Deque<K> cacheDeque;
    private final HashMap<K,V> cacheMap;
    private final int size;
    public LRUCacheWithHM(int size) {
        this.cacheDeque = new ArrayDeque<>(size);
        this.cacheMap = new HashMap<>(size);
        this.size = size;
    }
    public void put(K key,V value) {
        if (cacheMap.size() >= size) {
            K removedKey = removeEldest();
            if (removedKey != null) cacheMap.remove(removedKey);
        }
        if (!cacheMap.containsKey(key)) {
            cacheMap.put(key,value);
            cacheDeque.addFirst(key);
        } else cacheMap.put(key,value);
    }
    private K removeEldest() {
        return cacheDeque.removeLast();
    }
    public V get(K key) {
        if (cacheDeque.remove(key)) {
            cacheDeque.addFirst(key);
        }
        return cacheMap.get(key);
    }
    public Map<K,V> getEldestElement() {
        Map<K,V> result = new HashMap<>();
        K key = cacheDeque.getFirst();
        if (key != null) {
            V value = cacheMap.get(key);
            result.put(key,value);
        }
        return result;
    }
    public HashMap<K,V> getAll() {
        HashMap<K,V> cacheList = new LinkedHashMap<>();
        for (K key : cacheDeque) {
            cacheList.put(key,cacheMap.get(key));
        }
        return cacheList;
    }
}
