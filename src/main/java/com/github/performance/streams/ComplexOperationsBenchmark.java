package com.github.performance.streams;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.Arrays;

/**
 * Defines benchmarks that execute a somewhat complex string manipulation.
 * <p>
 * Note that there are no methods special-handling boxing or unboxing since
 * the reduction operation is based on a reference type (String).
 */
public class ComplexOperationsBenchmark extends AbstractIterationBenchmark {

    // array

    @Benchmark
    public String array_for() {
        String result = "";
        for (int i = 0; i < intArray.length; i++)
            result = stringOperation(result, intArray[i]);
        return result;
    }

    @Benchmark
    public String array_forEach() {
        String result = "";
        for (int intValue : intArray)
            result = stringOperation(result, intValue);
        return result;
    }

    // 34.024 ±        0.520   ms/op
    @Benchmark
    public String array_stream() {
        return Arrays
                .stream(intArray)
                .mapToObj(i -> i + "")
                .reduce("", this::stringOperation);
    }

    // 11.680 ±       1.041   ms/op
    @Benchmark
    public String array_stream_parallel() {
        return Arrays
                .stream(intArray)
                .parallel()
                .mapToObj(i -> i + "")
                .reduce("", this::stringOperation);
    }

    // list

    @Benchmark
    public String list_for() {
        String result = "";
        for (int i = 0; i < intList.size(); i++)
            result = stringOperation(result, intArray[i]);
        return result;
    }

    @Benchmark
    public String list_forEach() {
        String result = "";
        for (int intValue : intList)
            result = stringOperation(result, intValue);
        return result;
    }

    // 39.176 ±        0.371   ms/op
    @Benchmark
    public String list_stream() {
        return intList
                .stream()
                .map(i -> i + "")
                .reduce("", this::stringOperation);
    }

    // 13.911 ±       0.740   ms/op
    @Benchmark
    public String list_stream_parallel() {
        return intList
                .stream()
                .parallel()
                .map(i -> i + "")
                .reduce("", this::stringOperation);
    }

}
