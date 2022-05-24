package com.geekbrains.cloud;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatHandler implements Runnable {

    private DataInputStream is;
    private DataOutputStream os;

    public ChatHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = is.readUTF();
                System.out.println("received: " + msg);
                os.writeUTF(msg);
                os.flush();
            }
        } catch (Exception e) {
            System.err.println("Connection was broken");
        }
    }
}
