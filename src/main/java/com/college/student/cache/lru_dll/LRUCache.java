package com.college.student.cache.lru_dll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache<K, V> {
    private final int size;
    private final Map<K, V> cacheMap;
    private final LRU_DoubleLinkedList<K> dlldDemo;

    public LRUCache(int size) {
        this.size = size;
        cacheMap = new HashMap<>(size);
        dlldDemo = new LRU_DoubleLinkedList<>();
    }

    public void put(K key, V value) {
        if (cacheMap.size() >= size) removeEldest();
        if (!cacheMap.containsKey(key)) {
            cacheMap.put(key, value);
            dlldDemo.addFirst(key);
        } else cacheMap.put(key, value);
    }

    public void remove(K key) {
        if (!cacheMap.containsKey(key)) {
            cacheMap.remove(key);
            dlldDemo.remove(key);
        }
    }

    public V get(K key) {
        V element = (V) dlldDemo.get(key);
        return cacheMap.get(key);
    }

    public void displayAll() {
        List<K> keySet = dlldDemo.getAll();
        for (K key : keySet) {
            System.out.println(key + " " + cacheMap.get(key));
        }
    }

    public void removeEldest() {
        K key = dlldDemo.removeLast();
        cacheMap.remove(key);
    }

    public int size() {
        return cacheMap.size();
    }

    public Map<K, V> getAll() {
        List<K> keySet = dlldDemo.getAll();
        Map<K, V> entrySet = new HashMap<>();
        for (K key : keySet) {
            V value = cacheMap.get(key);
            if (value != null) {
                entrySet.put(key, value);
            }
        }
        return entrySet;
    }


}
