package main.java.filter.events;

import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;

import utils.Player;
import org.luaj.vm2.LuaValue;
import main.java.Main;
import org.luaj.vm2.LuaValue;
import main.java.Main;

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
        System.out.println("Enviando evento de entrada para todos os jogadores.");
        // Call Lua callback if registered by luacpp.addEvent
        

        SendEvent.sendEvent("EU:" + jogador, clients, socket);
    }
}
