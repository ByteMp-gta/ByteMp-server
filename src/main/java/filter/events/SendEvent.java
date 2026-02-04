package main.java.filter.events;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;

import utils.Player;

public class SendEvent {
    public static void sendEvent(String msg, Set<Player> eu, DatagramSocket socket ){
        byte[] data = msg.getBytes();
        for(Player player: eu){
            DatagramPacket packet = new DatagramPacket(data, data.length, player.getSocketAddress());
            try{
                socket.send(packet);
            }catch(IOException e ){
                System.out.println("ocoreu um erro ao enviar um evento");
            }
            
        }
    }
}
