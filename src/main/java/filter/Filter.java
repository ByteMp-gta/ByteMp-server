package main.java.filter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;

import main.java.filter.events.MovimentPlayer;
import main.java.filter.events.OnPlayerDamage;
import main.java.filter.events.OnPlayerEnter;
import utils.Player;

public class Filter {

    public static void  start(String msg, DatagramPacket packet, Set<Player> players, DatagramSocket socket){
        OnPlayerEnter.event(msg, packet, players, socket);
        OnPlayerDamage.playerDamage(msg, packet, players, socket);
        MovimentPlayer.movimentPlayer(msg, packet, players, socket);
    }
}
