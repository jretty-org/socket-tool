package org.jretty.socket;

import java.io.IOException;

import org.jretty.socket.tcp.SocketServer;

public class SocketServerTest {

    public static void main(String[] args) {
        final int serverPort = 8080;
        
        SocketServer server = new SocketServer() {
            public final static char START_FLAG = '^';
            public final static char END_FLAG = '$';
            private final static String RESPONSE_MSG = "OK";

            @Override
            public String ack(String msg) {
                if (msg.charAt(0) == START_FLAG && msg.charAt(msg.length() - 1) == END_FLAG) {
                    System.out.println("SUCCESS:\t" + msg);
                    return RESPONSE_MSG;
                } else {
                    System.out.println(msg.charAt(0));
                    System.out.println(START_FLAG);
                    System.out.println(msg.charAt(msg.length() - 1));
                    System.out.println(END_FLAG);
                    System.out.println("FAIL:\t" + msg);
                    return null;
                }
            }

            @Override
            public void handleMsg(String msg) {

            }
        };
        try {
            server.setDefaultServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.setRunning(false);
        try {
            server.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
