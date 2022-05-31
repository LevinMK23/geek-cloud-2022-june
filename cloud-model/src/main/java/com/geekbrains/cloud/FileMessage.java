package com.geekbrains.cloud;


import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
public class FileMessage implements CloudMessage {

    private final long size;
    private final byte[] data;

    private final String name;

    public FileMessage(Path path) throws IOException {
        size = Files.size(path);
        data = Files.readAllBytes(path);
        name = path.getFileName().toString();
    }

}
