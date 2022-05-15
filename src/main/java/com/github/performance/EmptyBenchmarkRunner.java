package com.github.performance;

import com.github.performance.strings.StringCompositeKeyBenchmark;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class EmptyBenchmarkRunner {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(StringCompositeKeyBenchmark.class.getSimpleName())
                .shouldFailOnError(true)
                .addProfiler(GCProfiler.class)// memory and GC profiler
                .build();

        new Runner(opt).run();
    }

}
