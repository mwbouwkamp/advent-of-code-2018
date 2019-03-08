package Day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class extremesCollector implements Collector<Dot, Map<String, Integer>, Map<String , Integer>> {

    @Override
    public Supplier<Map<String, Integer>> supplier() {
        Map<String, Integer> map = new HashMap<>();
        map.put("xMin", Integer.MAX_VALUE);
        map.put("yMin", Integer.MAX_VALUE);
        map.put("xMax", Integer.MIN_VALUE);
        map.put("yMax", Integer.MIN_VALUE);
        return () -> map;
    }

    @Override
    public BiConsumer<Map<String, Integer>, Dot> accumulator() {
        return (a, b) -> {
            a.put("xMin", Math.min(a.get("xMin"), b.point.x));
            a.put("yMin", Math.min(a.get("yMin"), b.point.y));
            a.put("xMax", Math.max(a.get("xMax"), b.point.x));
            a.put("yMax", Math.max(a.get("yMax"), b.point.y));
        };
    }

    @Override
    public Function<Map<String, Integer>, Map<String, Integer>> finisher() {
        return Function.identity();
    }

    @Override
    public BinaryOperator<Map<String, Integer>> combiner() {
        return (a, b) -> {
            a.put("xMin", Math.min(a.get("xMin"), b.get("xMin")));
            a.put("yMin", Math.min(a.get("yMin"), b.get("yMin")));
            a.put("xMax", Math.max(a.get("xMax"), b.get("xMax")));
            a.put("yMax", Math.max(a.get("yMax"), b.get("yMax")));
            return a;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> characteristics = new HashSet<>();
        characteristics.add(Characteristics.UNORDERED);
        characteristics.add(Characteristics.CONCURRENT);
        characteristics.add(Characteristics.IDENTITY_FINISH);
        return characteristics;
    }
}
