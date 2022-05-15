package com.github.performance.streams.parallel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.github.performance.PerformanceUtil.measureSumPerf;

// Also look at ->>> https://github.com/keaz/java-stream-benchmark
public class StreamCollectionsDemo {

    public static void main(String[] args) {

        // Result almost same with LongStream.range
        System.out.println("ArrayList: ");
        List<Long> arrayList = LongStream.rangeClosed(0L, 10_000_000).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        long arrayListTimeMs = measureSumPerf(arrayList);
        System.out.println();

        // Result almost same with Stream.iterate
        System.out.println("LinkedList: ");
        List<Long> linkedList = LongStream.rangeClosed(0L, 10_000_000).boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        long linkedListTimeMs = measureSumPerf(linkedList);
        System.out.println();

        System.out.println("HashSet: ");
        Set<Long> hashSet = LongStream.rangeClosed(0L, 10_000_000).boxed()
                .collect(Collectors.toCollection(HashSet::new));
        long hashSetTimeMs = measureSumPerf(hashSet);
        System.out.println();

        System.out.println("TreeSet: ");
        Set<Long> treeSet = LongStream.rangeClosed(0L, 10_000_000).boxed()
                .collect(Collectors.toCollection(TreeSet::new));
        long treeSetTimeMs = measureSumPerf(treeSet);
        System.out.println();

        System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("ArrayList sum done in: " + arrayListTimeMs  + " msecs");
        System.out.println("LinkedList sum done in: " + linkedListTimeMs + " msecs");
        System.out.println("HashSet sum done in: " + hashSetTimeMs + " msecs");
        System.out.println("TreeSet sum done in: " + treeSetTimeMs + " msecs");
    }
}
