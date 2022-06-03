package com.geekbrains.cloud.stream;

@FunctionalInterface
public interface Operation {

    int apply(int x, int y);

}
