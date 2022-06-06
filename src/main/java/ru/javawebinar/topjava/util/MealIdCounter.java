package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class MealIdCounter {
    public static AtomicInteger count = new AtomicInteger(0);

    public static int next(){
        return count.incrementAndGet();
    }
}
