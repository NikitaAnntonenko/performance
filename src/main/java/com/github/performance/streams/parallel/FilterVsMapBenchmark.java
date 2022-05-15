package com.github.performance.streams.parallel;

import com.github.performance.streams.parallel.domain.OperatorType;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"}, value = 1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 10, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class FilterVsMapBenchmark {

    @Param({ "MAP", "FILTER" })
    private OperatorType operatorType = OperatorType.MAP;

    @Param({ "10000000" })
    private int number = 10_000_000;

    @Benchmark
    public long reduceSum() {

        var numbers = LongStream
                .rangeClosed(1, number);

        switch (operatorType) {
            case FILTER:
                numbers = numbers.filter(i -> true);
                break;
            case MAP:
                numbers = numbers.map(i -> i);
                break;
        }

        return numbers.parallel().reduce(0L, Long::sum);
    }

}
