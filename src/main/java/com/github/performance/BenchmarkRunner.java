package com.github.performance;

import com.github.performance.maps.HashMapVsEnumMapInstantiationBenchmark;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(HashMapVsEnumMapInstantiationBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(1))
                .forks(0) // 0 makes debugging possible
                .shouldFailOnError(true)
//				.shouldDoGC(false)
                .jvmArgsAppend(
//						"-Xint",
//						"-XX:+UnlockDiagnosticVMOptions",
//						"-XX:TieredStopAtLevel=1",
//						"-XX:+PrintCompilation",
//						"-XX:+PrintInlining",
//						"-XX:+LogCompilation"
                )
                .addProfiler(GCProfiler.class)// memory and GC profiler
                .build();

        new Runner(opt).run();
    }
}
