package com.gestaoensino.gestao_ensino.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SuppressWarnings("java:S2176")
public final class CollectionUtils extends org.springframework.util.CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * Retorna uma Lista a partir de um iterable
     *
     * @param itr the {@link Iterable}
     * @return uma lista
     */
    public static <T> List<T> getListFromIterable(Iterable<T> itr) {
        List<T> cltn = new ArrayList<>();
        itr.forEach(cltn::add);
        return cltn;
    }

    /**
     * Executa to Map aceitando valores nulos
     *
     * @param keyMapper   o getter da chave
     * @param valueMapper o getter do valor
     * @return Collector
     */
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMapOfNullables(Function<? super T, ? extends K> keyMapper,
                                                                        Function<? super T, ? extends U> valueMapper) {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    Map<K, U> map = new LinkedHashMap<>();
                    list.forEach(item -> {
                        K key = keyMapper.apply(item);
                        U value = valueMapper.apply(item);
                        if (map.containsKey(key)) {
                            throw new IllegalStateException(String.format(
                                    "Duplicate key %s (attempted merging values %s and %s)",
                                    key, map.get(key), value));
                        }
                        map.put(key, value);
                    });
                    return map;
                }
        );
    }

    /**
     * Executa distinct by
     *
     * @param keyExtractor o getter
     * @return Predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
