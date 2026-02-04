package main.java.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;

import main.java.filter.events.OnPlayerEnter;
import utils.LoaderFile;
import utils.Player;
import java.net.InetAddress;

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

            if (mensagem.contains("EU:")) {
                System.out.println("passou");
                clients.add(new Player(packet.getSocketAddress(), mensagem));

                String nomeJogador = mensagem.substring(3);

                try {
                    DatagramSocket forward = new DatagramSocket();
                    InetAddress addr = InetAddress.getByName("127.0.0.1");

                  
                    String forwardMsg = "EU:" + nomeJogador;
                    byte[] fdata = forwardMsg.getBytes();
                    DatagramPacket fpacket = new DatagramPacket(fdata, fdata.length, addr, LoaderFile.loadIntPort("saida.txt"));
                    System.out.println("Enviando pacote para " + addr + ":" + LoaderFile.loadIntPort("saida.txt")   );
                    forward.send(fpacket);

                    forward.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                OnPlayerEnter onPlayerEnter = new OnPlayerEnter(clients, socket);
                onPlayerEnter.sendEvent(nomeJogador);
            }

            String resposta = "Echo: " + mensagem;
            byte[] respostaBytes = resposta.getBytes();

            DatagramPacket respostaPacket = new DatagramPacket(
                    respostaBytes,
                    respostaBytes.length,
                    packet.getAddress(),
                    packet.getPort());
            try {
                socket.send(respostaPacket);
            } catch (IOException e) {
                System.out.println("deu bom nao");
                System.exit(1);
            }

        }

    }

}
