package main.java.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;

import main.java.filter.Filter;
import utils.Player;
;

public class Server {

    private int port;
    private DatagramSocket socket;
    private Set<Player> clients = new HashSet<>();

    public Server(int port) {
        this.port = port;
    }

    public boolean connect() {

        try {

            this.socket = new DatagramSocket(this.port);
            System.out.println("servidor rodando na porta " + this.port);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public void start() {
        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {

                socket.receive(packet);

            } catch (IOException e) {

                System.out.println("erro ao enviar dados ");
                System.exit(1);

            }

            String mensagem = new String(
                    packet.getData(), 0, packet.getLength());

            System.out.println("Recebido: " + mensagem);

            Filter.start(mensagem, packet, clients, socket);

            
           

        }

    }

}
