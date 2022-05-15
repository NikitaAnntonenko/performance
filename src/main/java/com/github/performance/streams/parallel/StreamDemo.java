package com.github.performance.streams.parallel;

import static com.github.performance.PerformanceUtil.measureSumPerf;

public class StreamDemo {

    public static void main(String[] args) {
        System.out.println("Cores: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Sequential sum done in: " +
                measureSumPerf(ParallelStreams::parallelRangedMappedSum, 10_000_000) + " msecs");
    }


}
