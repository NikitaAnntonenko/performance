package com.github.performance.streams.parallel;

import org.openjdk.jmh.annotations.*;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"}, value = 1)
@Warmup(iterations = 2, time = 30)
@Measurement(iterations = 2, time = 30)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class FactorialBenchmark {

    @Param({ "false", "true" })
    private boolean parallel = false;

    @Param({ "200000" })
    private int number = 200_000;

    @Benchmark
    public BigInteger factorial() {
        var numbers = IntStream
                .rangeClosed(1, number)
                .mapToObj(BigInteger::valueOf);
        if (parallel)
            numbers = numbers.parallel();
        return numbers.reduce(BigInteger.ONE, BigInteger::multiply);
    }

}
