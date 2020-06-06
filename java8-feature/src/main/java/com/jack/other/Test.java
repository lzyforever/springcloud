package com.jack.other;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 一些新语法
 */
public class Test {
    public static void main(String[] args) {
        // testMap();
        // testMap2();
        // testOperator();
    }

    /**
     * 遍历Map
     */
    private static void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("5", "abc");
        map.put("2", "eba");
        map.put("1", "gae");
        map.put("520", "ad");
        map.put("522", "gas");
        map.put("15", "ghasd");

        BiConsumer<String, String> cs = (k, v) -> {
            System.out.println("key : " + k + " value: " + v);
        };

        map.forEach(cs);
        // 等同于
        map.forEach((k, v) -> {
            System.out.println("key : " + k + " value: " + v);
        });
    }

    private static void testMap2() {
        List<String> items = Arrays.asList("apple", "apple", "banana",
                "apple", "orange", "banana", "papaya");

        Map<String, Long> result = items.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);
    }

    private static void testOperator() {
        BinaryOperator<Integer> binary = (x, y) -> x + y;
        System.out.println(binary.apply(1, 2));
    }


}
