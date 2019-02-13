package org.jretty.socket;

import java.net.InetAddress;
import java.net.SocketException;

import org.jretty.socket.udp.UDPServer;


public class UDPServerTest {

    public static void main(String[] args) {
        final int clientPort = 9000;
        final int serverPort = 3000;
        
        UDPServer server = new UDPServer() {
            public final static char START_FLAG = '^';
            public final static char END_FLAG = '$';
            private final static String RESPONSE_MSG = "OK";

            @Override
            public String ack(String msg) {
                if (msg.charAt(0) == START_FLAG && msg.charAt(msg.length() - 1) == END_FLAG) {
                    System.out.println("SUCCESS:\t" + msg);
                    return RESPONSE_MSG;
                } else {
                    System.out.println("FAIL:\t" + msg);
                    return null;
                }
            }

            @Override
            public void handleMsg(String msg) {

            }

            @Override
            public int getClientPort(InetAddress clientAddress) {
                return clientPort;
            }
        };
        try {
            server.setDefaultDs(serverPort);
        } catch (SocketException e) {
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
        server.close();

    }

}