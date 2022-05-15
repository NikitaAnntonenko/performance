package com.github.performance.streams.parallel;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    // the whole list of numbers isn’t available at the beginning of the reduction process
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    // ===================================================
    public static long iterativeBoxedSum(long n) {
        Long result = 0L;
        for (long i = 1L; i <= n; i++) {
            result = Long.sum(result, i);
        }
        return result;
    }

    public static long sequentialUnboxedSum(long n) {
        return LongStream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long parallelUnboxedSum(long n) {
        return LongStream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    // ===================================================

    // This is evidence that choosing the right data structures is often more important than parallelizing the algorithm that uses them.
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }


    /*
    * The characteristics of a stream, and how the intermediate operations through the pipeline modify them, can change
    * the performance of the decomposition process. For example, a SIZED stream can be divided into two equal parts,
    * and then each part can be processed in parallel more effectively, but a filter operation can throw away an
    * unpredictable number of elements, making the size of the stream itself unknown.
    */

    public static long parallelRangedFilteredSum(long n) {
        return LongStream.rangeClosed(1, n)
                .filter(i -> i != 10)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedMappedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .map(i -> i + 1)
                .parallel()
                .reduce(0L, Long::sum);
    }

    /*
     * Nevertheless, keep in mind that parallelization doesn’t come for free. The parallelization process itself
     * requires you to recursively partition the stream, assign the reduction operation of each substream to a
     * different thread, and then combine the results of these operations in a single value. But moving data between
     * multiple cores is also more expensive than you might expect, so it’s important that work to be done in parallel
     * on another core takes longer than the time required to transfer the data from one core to another. In general,
     * there are many cases where it isn’t possible or convenient to use parallelization. But before you use a parallel
     * Stream to make your code faster, you have to be sure that you’re using it correctly;
     *
     * It’s not helpful to produce a result in less time if the result will be wrong!!! :)
     */

    // ===================== ERRORS =====================

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        public long total = 0;
        public void add(long value) { total += value; }
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class AtomicAccumulator {
        public AtomicLong total = new AtomicLong(0L);
        public void add(long value) {
            total.addAndGet(value);
        }
    }

    public static long sideEffectAtomicParallelSum(long n) {
        AtomicAccumulator accumulator = new AtomicAccumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total.get();
    }

    // NO NO NO !!!!!
    public static long sideEffectAtomicIteratorParallelSum(long n) {
        AtomicAccumulator accumulator = new AtomicAccumulator();
        Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .forEach(accumulator::add);
        return accumulator.total.get();
    }

}
