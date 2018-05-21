package com.learning.netty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class Server {

    @Test
    public void server() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("server on");
        while(true) {
            Socket socket = server.accept();
            System.out.println("client port: " + socket.getPort());
        }
    }
}
