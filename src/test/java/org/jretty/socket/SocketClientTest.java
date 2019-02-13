package org.jretty.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jretty.socket.tcp.SocketClient;
import org.jretty.socket.tcp.SocketClientImpl;

public class SocketClientTest {

    public static void main(String[] args) {
        final int serverPort = 8080;

        SocketClientImpl sc = new SocketClientImpl();
        try {
            sc.setDistHost("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("localIP: " + getSpecialHostAddress(sc.getDistHost()).getHostAddress());
        sc.setDistPort(serverPort);

        SocketClient client = sc;

        int n = 2000;
        long start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            String msg = "^" + System.currentTimeMillis() + " - Hello SocketServer................$";
            client.sendQuiet(buildMsg(msg, 10));
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println((System.currentTimeMillis() - start) / (n * 1.0)); // 1.8 ms/条
    }

    public static InetAddress getSpecialHostAddress(String hostName) {
        try {
            return InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    static String buildMsg(String org, int times) {
        StringBuilder sbu = new StringBuilder((int) (org.length() * times * 1.2) + 16);
        for (int i = 0; i < times; i++) {
            sbu.append(org);
        }
        return sbu.toString();
    }

}