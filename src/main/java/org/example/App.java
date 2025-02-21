package org.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        Thread servidor = new Thread(new Servidor(15000),"Servidor");
        Thread client = new Thread(new Client(InetAddress.getByName("127.0.0.1"),15000),"Client");

        servidor.start();
        Thread.sleep(2000);
        client.start();

        servidor.join();
        client.join();
    }
}
