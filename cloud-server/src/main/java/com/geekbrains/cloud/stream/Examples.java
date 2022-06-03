package com.geekbrains.cloud.stream;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Examples {

    private static Integer[][] ARRAY = new Integer[][]{
            {1, 2, 3},
            {4},
            {5, 6, 7, 8},
            {9, 10},
            {11}
    };

    public static void main(String[] args) throws IOException {

        // filter odd and print
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(x -> x % 2 == 0)
                .forEach(System.out::println);

        // increment all values by 1 and collect to list
        List<Integer> list = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .map(x -> x + 1)
                .toList();

        System.out.println(list);

        // calculate sum
        Integer result = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .reduce(0, Integer::sum);

        System.out.println(result);

        // words counter
        Map<String, Integer> words = Files.lines(Path.of("server_files", "1.txt"))
                .flatMap(s -> Stream.of(s.split(" +")))
                .map(String::toLowerCase)
                .map(s -> s.replaceAll("\\W|\\d", ""))
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toMap(
                        Function.identity(),
                        value -> 1,
                        Integer::sum
                ));
        System.out.println(words);

        // counter rate
        words.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

        // flatMap array
        List<Integer> values = Arrays.stream(ARRAY)
                .flatMap(Stream::of)
                .toList();

        System.out.println(values);

    }
}
