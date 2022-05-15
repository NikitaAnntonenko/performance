package com.github.performance;

import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;

public class PerformanceUtil {

    public static long measure(boolean debugLog, String header, Runnable runnable) {
        long fastest = Long.MAX_VALUE;
        if (debugLog) {
            System.out.println();
            System.out.println(header.toUpperCase(Locale.ENGLISH));
        }
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            runnable.run();
            long duration = (System.nanoTime() - start) / 1_000_000;
            if (debugLog) System.out.println( (i + 1) + ". Duration: " + duration);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum + ". Duration: " + duration);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static long measureSumPerf(Collection<Long> collection) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = collection.parallelStream().reduce(0L, Long::sum);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum + ". Duration: " + duration);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }
}
