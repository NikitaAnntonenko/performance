package com.github.performance.strings;

import com.github.performance.strings.domain.Key;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 10, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"}, value = 1)
public class StringCompositeKeyBenchmark {

    @Benchmark
    public Object concatenation(Data data) {
        return data.stringObjectMap.get(data.code + '_' + data.locale);
    }

    @Benchmark
    public Object compositeKey(Data data) {
        return data.keyObjectMap.get(new Key(data.code, data.locale));
    }

    @Benchmark
    public Object list(Data data) {
        return data.listObjectMap.get(Arrays.asList(data.code, data.locale));
    }

    @Benchmark
    public Object mapInMap(Data data) {
        return data.mapInMap.get(data.code).get(data.locale);
    }

    @State(Scope.Thread)
    public static class Data {
        private final String code = "code1";
        private final Locale locale = Locale.getDefault();

        private final HashMap<String, Object> stringObjectMap = new HashMap<>();
        private final HashMap<Key, Object> keyObjectMap = new HashMap<>();
        private final HashMap<List<?>, Object> listObjectMap = new HashMap<>();
        private final HashMap<String, Map<Locale, Object>> mapInMap = new HashMap<>();

        @Setup
        public void setUp() {
            stringObjectMap.put(code + '_' + locale, new Object());

            keyObjectMap.put(new Key(code, locale), new Object());

            listObjectMap.put(Arrays.asList(code, locale), new Object());

            HashMap<Locale, Object> localeObjectMap = new HashMap<>();
            localeObjectMap.put(locale, new Object());
            mapInMap.put(code, localeObjectMap);
        }
    }
}
