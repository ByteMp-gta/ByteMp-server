package main.java.filter.events;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import utils.LoaderFile;
import utils.Player;

public class OnPlayerEnter {
    private Set<Player> clients = new HashSet<>();
    private DatagramSocket socket;

    public OnPlayerEnter() {
    }

    public OnPlayerEnter(Set<Player> clients, DatagramSocket socket) {
        this.clients = clients;
        this.socket = socket;
    }

    public void sendEvent(String jogador) {

        // Call Lua callback if registered by luacpp.addEvent

        SendEvent.sendEvent("EU:" + jogador, clients, socket);
    }

    public static void event(String msg, DatagramPacket packet,  Set<Player> clients, DatagramSocket socket) {
        if (msg.contains("EU:")) {
            
            

            String nomeJogador = msg.substring(3);

            clients.add(new Player(packet.getSocketAddress(), nomeJogador));
            try {
                DatagramSocket forward = new DatagramSocket();
                InetAddress addr = InetAddress.getByName("127.0.0.1");

                String forwardMsg = "EU:" + nomeJogador;
                byte[] fdata = forwardMsg.getBytes();
                DatagramPacket fpacket = new DatagramPacket(fdata, fdata.length, addr,
                        LoaderFile.loadIntPort("saida.txt"));

                forward.send(fpacket);

                forward.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            OnPlayerEnter onPlayerEnter = new OnPlayerEnter(clients, socket);
            onPlayerEnter.sendEvent(nomeJogador);
        }
    }
}
