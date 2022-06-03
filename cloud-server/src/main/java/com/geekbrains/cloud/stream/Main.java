package com.geekbrains.cloud.stream;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int calculate(int x, int y, Operation operation) {
        return operation.apply(x, y);
    }

    public static void main(String[] args) {
        // 1. Lambda
        Operation mul = (x, y) -> x * y;

        // 2. Method reference
        Operation sum = Integer::sum;
        Operation div = Main::div;

        System.out.println(calculate(1, 2, sum));
        System.out.println(calculate(2, 2, mul));
        System.out.println(calculate(10, 2, div));

        Func func = Main::foo;

        System.out.println(func.repeat(5, "Hello"));
    }

    private static List<String> foo(int x, String s) {
        ArrayList<String> list = new ArrayList<>(x);
        for (int i = 0; i < x; i++) {
            list.add(s);
        }
        return list;
    }

    private static int div(int x, int y) {
        return x / y;
    }
}
