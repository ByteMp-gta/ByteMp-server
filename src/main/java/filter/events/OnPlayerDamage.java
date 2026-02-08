package main.java.filter.events;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;


import utils.LoaderFile;
import utils.Player;

public class OnPlayerDamage {

    public static void playerDamage(String msg, DatagramPacket packet, Set<Player> clients, DatagramSocket socket) {
        System.out.println("mas " + msg);
        if (msg.startsWith("onPlayerDamage::")) {

            String[] parts = msg.split("::");

            if (parts.length == 3) {
                String nome = parts[1];
                float dano = Float.parseFloat(parts[2]);

                boolean foiEncontrado = true;
                /* 
                for (Player p : clients) {
                    if (p.getName().equals(nome)) {
                        foiEncontrado = true;
                        break;
                    }
                }
                 */

                if (foiEncontrado) {
                    try {
                        DatagramSocket forward = new DatagramSocket();
                        InetAddress addr = InetAddress.getByName("127.0.0.1");

                        String forwardMsg = "onPlayerDamage:" + nome + ":Dano:" + dano;

                        byte[] fdata = forwardMsg.getBytes();
                        DatagramPacket fpacket = new DatagramPacket(
                                fdata,
                                fdata.length,
                                addr,
                                LoaderFile.loadIntPort("saida.txt"));

                        forward.send(fpacket);
                        System.out.println("enviado");
                        forward.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
