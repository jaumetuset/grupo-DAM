package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
    private InetAddress serverIP;
    private int serverPort;

    public Client(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.print("(Client) Introduce comando (backup/exit): ");
                String command = sc.nextLine();

                Socket socketToServer = new Socket(serverIP, serverPort);
                DataOutputStream fluxOut = new DataOutputStream(socketToServer.getOutputStream());
                DataInputStream fluxIn = new DataInputStream(socketToServer.getInputStream());

                fluxOut.writeUTF(command);
                String response = fluxIn.readUTF();
                System.out.println("(Server) Responde: " + response);

                if ("Cerrando servidor...".equals(response)) {
                    running = false;
                }

                socketToServer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

