package com.github.performance.maps;

import com.github.performance.maps.domain.Constants;
import com.github.performance.maps.domain.ConstantsEnum;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(jvmArgsAppend = {"-Xms2g", "-Xmx2g"})
public class HashMapVsEnumMapIterationBenchmark {

  @Benchmark
  public void hashMap(Data data, Blackhole bh) {
    var map = data.hashMap;

    for (String key : data.hashMapKeySet) {
      bh.consume(map.get(key));
    }
  }

  @Benchmark
  public void enumMap(Data data, Blackhole bh) {
    var map = data.enumMap;

    for (ConstantsEnum key : data.enumMapKeySet) {
      bh.consume(map.get(key));
    }
  }

  @State(Scope.Thread)
  public static class Data {
    private HashMap<String, Integer> hashMap;
    private EnumMap<ConstantsEnum, Integer> enumMap;
    private Set<String> hashMapKeySet;
    private Set<ConstantsEnum> enumMapKeySet;

    @Setup
    public void setup() {
      HashMap<String, Integer> hashMap = new HashMap<>();
      hashMap.put(Constants.MarginLeft, 1);
      hashMap.put(Constants.MarginRight, 2);
      hashMap.put(Constants.MarginTop, 3);
      hashMap.put(Constants.MarginBottom, 4);

      EnumMap<ConstantsEnum, Integer> enumMap = new EnumMap<>(ConstantsEnum.class);
      enumMap.put(ConstantsEnum.MarginLeft, 1);
      enumMap.put(ConstantsEnum.MarginRight, 2);
      enumMap.put(ConstantsEnum.MarginTop, 3);
      enumMap.put(ConstantsEnum.MarginBottom, 4);

      this.hashMap = hashMap;
      this.enumMap = enumMap;

      this.hashMapKeySet = hashMap.keySet();
      this.enumMapKeySet = enumMap.keySet();
    }
  }
}
