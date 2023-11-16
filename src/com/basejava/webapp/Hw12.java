package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;

public class Hw12 {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(6, 5, 7, 4, 6, 3, 8, 7)));
        System.out.println(oddOrEven(Arrays.asList(6, 5, 7, 4, 6, 3, 8, 7, 1)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(x ->
                (integers.stream().reduce(0, Integer::sum) % 2 == 0 ? x % 2 != 0 : x % 2 == 0)).toList();
    }
}
