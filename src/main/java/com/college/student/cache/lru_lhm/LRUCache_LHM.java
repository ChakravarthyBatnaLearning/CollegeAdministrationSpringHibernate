package com.college.student.cache.lru_lhm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache_LHM<K, V> {
    private static volatile LinkedHashMap<?, ?> lruCache_lhm;

    public static LinkedHashMap<?, ?> getLruCache_lhm(int size) {
        if (lruCache_lhm == null) {
            synchronized (LRUCache_LHM.class) {
                if (lruCache_lhm == null) {
                    lruCache_lhm = new LinkedHashMap<>(size, 0.75f, true) {
                        @Override
                        public boolean removeEldestEntry(Map.Entry entry) {
                            return size() >= size;
                        }
                    };
                }
            }
        }
        return lruCache_lhm;
    }
}