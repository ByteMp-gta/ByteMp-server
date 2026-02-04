#include "cpp/LuaEngine.h"
#include "network/connection.hpp"
#include <iostream>
#include <fstream>
#include <string>

int trocarPort(int port, const std::string& ip_server) {
    ServerSocket server(port, ip_server, "lua_server");
    
    if (!server.conecttionServer()) {
        return trocarPort(port + 1, ip_server);
    }
    else {
        std::cout << "Servidor conectado na porta: " << port << std::endl;
        return port;
    }
}

int main() {
    LuaEngine lua;
    if (!lua.runScript("./exemple/main.lua")) {
        std::cerr << "Falha ao carregar o script Lua" << std::endl;
        
    }

    ServerSocket server(PORT, SERVER_IP , "lua_server");

    if (!server.conecttionServer()) {
        trocarPort(PORT, SERVER_IP);
    }

    server.listenEvents(&lua);

    return 0;
}
