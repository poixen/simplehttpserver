package com.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Container for accessing counter data
 *
 * @author matthew.lowe
 */
public class Counters {

    private static Map<String, CounterBean> counters = Collections.synchronizedMap(new HashMap<String, CounterBean>());

    /**
     * Loads persistent data into the container
     */
    public static void loadPersistentData() {
        // can load from a db or any other source here
        counters.put("visits", new CounterBean("visits", 0));
    }

    /**
     * Saves persistent data
     */
    public static void savePersistentData() {
        // can save the data to a db or any other source
    }


    /**
     * Removes all counters
     */
    public static void clearCounters() {
        counters.clear();
    }


    /**
     * Returns if the specified key is in the collection.
     *
     * @param key key to check for
     * @return {@code true} if key is found. {@code false} if not.
     */
    public static boolean containsKey(final String key) {
        return counters.containsKey(key);
    }


    /**
     * Returns the count mapped to the passed in {@code key}
     *
     * @return the count mapped to the key. If no key exists
     * then null will be returned as-per the {@link ConcurrentHashMap} API.
     */
    public static CounterBean get(final String key) {
        return counters.get(key);
    }


    /**
     * Adds an entry to the collection. For more infomation look
     * at the {@link ConcurrentHashMap} Documentation.
     *
     * @param key   key the count will be mapped to
     * @param value count to store
     */
    public static void put(final String key, final int value) {
        counters.put(key, new CounterBean(key, value));
    }


    public static Collection<CounterBean> getCounterBeans() {
        return counters.values();
    }

}
