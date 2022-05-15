package com.github.performance.streams;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Benchmarks inspired by the comments on the post covering the other benchmarks.
 */
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"}, value = 1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 10, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class CommentOperationsBenchmark extends AbstractIterationBenchmark {

    // 0.095 ms/op ±  0.002
    @Benchmark
    public int array_max_for() {
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < intArray.length; i++)
            if (intArray[i] > m)
                m = intArray[i];
        return m;
    }

    @Benchmark
    public int stream_max_for() {
        return IntStream.of(intArray).max().getAsInt();
    }

    // 0.094 ms/op ±  0.001
    @Benchmark
    public int array_max_forEach() {
        int m = Integer.MIN_VALUE;
        for (int intValue : intArray)
            if (intValue > m)
                m = intValue;
        return m;
    }

    // 0.095 ms/op ±  0.002
    @Benchmark
    public int array_max_forWithException() {
        int m = Integer.MIN_VALUE;
        try {
            for (int i = 0; ; i++)
                if (intArray[i] > m)
                    m = intArray[i];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return m;
        }
    }

    // 0.095 ms/op ±  0.002
    @Benchmark
    public int array_min_for() {
        int m = Integer.MAX_VALUE;
        for (int i = 0; i < intArray.length; i++)
            if (intArray[i] < m)
                m = intArray[i];
        return m;
    }

    // 0.387 ms/op ±  0.017
    @Benchmark
    public int boxedArray_max_for() {
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < integerArray.length; i++)
            if (integerArray[i] > m)
                m = integerArray[i];
        return m;
    }

    // 0.395 ms/op ±  0.022
    @Benchmark
    public int boxedArray_max_forEach() {
        int m = Integer.MIN_VALUE;
        for (int intValue : integerArray)
            if (intValue > m)
                m = intValue;
        return m;
    }

    // 1.326 ms/op ±  0.356
    @Benchmark
    public int boxedArray_max_stream() {
        return Arrays.stream(integerArray).max(Math::max).get();
    }

    // 0.572 ms/op ±  0.013
    @Benchmark
    public int boxedArray_max_stream_unboxed() {
        return Arrays.stream(integerArray).mapToInt(x -> x).max().getAsInt();
    }

    // 0.464 ms/op ±  0.026
    @Benchmark
    public int list_max_for() {
        int m = Integer.MIN_VALUE;
        for (int i = 0; i < intList.size(); i++)
            if (intList.get(i) > m)
                m = intList.get(i);
        return m;
    }

    // 0.506 ms/op ±  0.018
    @Benchmark
    public int list_max_forEach() {
        int m = Integer.MIN_VALUE;
        for (int intValue : intList)
            if (intValue > m)
                m = intValue;
        return m;
    }

    // 2.173 ms/op ±  0.179
    @Benchmark
    public int list_max_stream() {
        return intList.stream().max(Math::max).get();
    }

    // 2.311 ms/op ±  0.025
    @Benchmark
    public int list_min_stream() {
        return intList.stream().min(Math::max).get();
    }

    // 0.583 ms/op ±  0.028
    @Benchmark
    public int list_max_stream_unboxed() {
        return intList.stream().mapToInt(x -> x).max().getAsInt();
    }

    // 0.567 ms/op ±  0.006
    @Benchmark
    public int list_min_stream_unboxed() {
        return intList.stream().mapToInt(x -> x).min().getAsInt();
    }

}
