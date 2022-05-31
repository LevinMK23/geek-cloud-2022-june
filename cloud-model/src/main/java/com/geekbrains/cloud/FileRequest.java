package com.geekbrains.cloud;

import lombok.Data;

@Data
public class FileRequest implements CloudMessage {

    private final String name;

}
