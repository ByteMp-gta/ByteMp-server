package main.java;

import main.java.server.Server;
import utils.RunLuaServer;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Iniciando servidor Java (porta 8080)...");
        Server server = new Server(8080);
        if (server.connect()) {
            RunLuaServer.run();
            server.start();
        } else {
            System.err.println("Falha ao iniciar servidor Java na porta 8080.");
        }
        
    }
}
