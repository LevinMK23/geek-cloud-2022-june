package com.geekbrains.cloud.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PathExamples {

    public static void main(String[] args) throws IOException {

        Path files = Path.of("server_files");
        Path main = files.resolve("main.fxml");

        Path path = main.resolve("..")
                .resolve("main.fxml")
                .resolve("..");

        System.out.println(path.normalize());

        System.out.println(main.toAbsolutePath());

        Files.writeString(
                Path.of("server_files", "test.txt"),
                "Hello world",
                StandardOpenOption.APPEND
        );




    }
}
