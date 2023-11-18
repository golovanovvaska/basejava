package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;

public class Hw12 {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(6, 5, 7, 4, 6, 3, 8, 7)));
        System.out.println(oddOrEven(Arrays.asList(6, 5, 7, 4, 6, 3, 8, 7, 1)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers
                .stream()
                .collect(partitioningBy(x -> x % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
