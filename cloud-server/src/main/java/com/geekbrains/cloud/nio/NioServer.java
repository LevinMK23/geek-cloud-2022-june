package com.geekbrains.cloud.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class NioServer {

    private Path current = Path.of(System.getProperty("user.home"));

    private ServerSocketChannel server;
    private Selector selector;

    public NioServer() throws IOException {
        server = ServerSocketChannel.open();
        selector = Selector.open();
        server.bind(new InetSocketAddress(8189));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() throws IOException {
        while (server.isOpen()) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept();
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }

        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) key.channel();
        StringBuilder s = new StringBuilder();
        while (channel.isOpen()) {
            int read = channel.read(buf);
            if (read < 0) {
                channel.close();
                return;
            }
            if (read == 0) {
                break;
            }
            buf.flip();
            while (buf.hasRemaining()) {
                s.append((char) buf.get());
            }
            buf.clear();
        }
        String message = s.toString().trim();
        if (message.startsWith("cd")) {
            String[] args = message.split(" +");
            if (args.length == 2) {
                String dir = args[1];
                Path target = current.resolve(dir);
                if (Files.exists(target)) {
                    if (Files.isDirectory(target)) {
                        current = target;
                        String response = "-> ";
                        channel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
                    } else {
                        String response = "arg should be directory\n\r-> ";
                        channel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
                    }
                } else {
                    String response = "directory is not exist\n\r-> ";
                    channel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
                }
            } else {
                String response = "command cd should have only 1 argument\n\r-> ";
                channel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
            }
        } else if (message.equals("ls")) {
            String files = Files.list(current)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.joining("\n\r")) + "\n\r-> ";
            channel.write(ByteBuffer.wrap(files.getBytes(StandardCharsets.UTF_8)));
        } else {
            String response = "Unknown command\n\r-> ";
            channel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
        }
    }

    private void handleAccept() throws IOException {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        channel.write(ByteBuffer.wrap("Welcome in Mike terminal!\n-> ".getBytes(StandardCharsets.UTF_8)));
    }
}
