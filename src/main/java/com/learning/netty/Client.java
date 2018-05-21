package com.learning.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class Client {
    
    private String host = "localhost";
    private int port = 8888;
    
    @Test
    public void client() throws UnknownHostException, IOException {
        for(int i=0;i<60000;i++) {
            Socket socket = new Socket(host, port);
            OutputStream out = socket.getOutputStream();
            chat(out);
            //socket.close();
        }
    }
    
    private void chat(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        pw.println("hello");
        pw.flush();
    }
}
