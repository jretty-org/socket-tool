package org.jretty.socket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.jretty.socket.udp.UDPClient;
import org.jretty.socket.udp.UDPClientImpl;

public class UDPClientTest {

    public static void main(String[] args) {
        final int clientPort = 9000;
        final int serverPort = 3000;

        UDPClientImpl uc = new UDPClientImpl();
        try {
            uc.setDefaultDs(clientPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            uc.setDistHost("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("localIP: " + uc.getDistAddress().getHostAddress());
        uc.setDistPort(serverPort);

        UDPClient client = uc;

        int n = 2000;
        long start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            String msg = "^" + System.currentTimeMillis() + " - Hello UDPserver................$";
            client.sendQuiet(buildMsg(msg, 10));
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println((System.currentTimeMillis() - start) / (n * 1.0)); // 0.29 ms/条

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
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