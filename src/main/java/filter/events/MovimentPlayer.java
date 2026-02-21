package main.java.filter.events;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.util.Set;


import utils.Player;

public class MovimentPlayer {
    public static void movimentPlayer(String msg, DatagramPacket packet, Set<Player> players, DatagramSocket socket) {
        // onMovimentPlayer::name::x::y::z
        if (msg.startsWith("onMovimentPlayer::")) {

            String[] parts = msg.split("::");

            if (parts.length == 5) {
                String nome = parts[1];
                float x = Float.parseFloat(parts[2]);
                float y = Float.parseFloat(parts[3]);
                float z = Float.parseFloat(parts[4]);

                boolean foiEncontrado = true;

                if (foiEncontrado) {
                    try {
                        for (Player player : players) {
                            if (!player.getName().equals(nome)) {
                                String msgSend = nome + "::" + x + "::" + y + "::" + z;
                                byte[] msgSendByte = msgSend.getBytes();

                                DatagramPacket pacote = new DatagramPacket(msgSendByte, msgSendByte.length,
                                        player.getSocketAddress());
                                socket.send(pacote);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
