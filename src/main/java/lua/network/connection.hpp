#ifndef CONNECTION
#define CONNECTION

#include <string>
#include <iostream>
#include <cstring>

#if defined(_WIN32) || defined(_WIN64)

#define WIN32_LEAN_AND_MEAN
#define _WINSOCKAPI_

#include <winsock2.h>
#include <ws2tcpip.h>
#include <windows.h>

#pragma comment(lib, "ws2_32.lib")

using socket_t = SOCKET;

#else

#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

using socket_t = int;

#define INVALID_SOCKET (-1)
#define SOCKET_ERROR (-1)
#define closesocket close

#endif

#define SERVER_IP "127.0.0.1"
#define PORT 8080
#include "../cpp/LuaEngine.h"
#include <fstream>
#include <iostream>
#include <string>
#include <sstream> 
#include <vector>
enum Event
{
    EU,
    SAI,
    SLA
};

class ServerSocket
{
private:
    sockaddr_in servaddr{};
    socket_t sockfd = INVALID_SOCKET;
    int port;
    std::string ip_server;

public:
    std::string name_user;

    ServerSocket(int port, const std::string &ip_server, const std::string &name_user)
        : port(port), ip_server(ip_server), name_user(name_user)
    {
    }
    void savePort(int port)
    {
        std::string nomeArquivo = "saida.txt";
        std::ofstream arquivo(nomeArquivo);
        arquivo << port;
        arquivo.close();
    }
    bool conecttionServer()
    {

#if defined(_WIN32) || defined(_WIN64)
        WSADATA wsaData;
        if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0)
        {
            std::cerr << "WSAStartup falhou\n";
            return false;
        }
#endif

        sockfd = socket(AF_INET, SOCK_DGRAM, 0);
        if (sockfd == INVALID_SOCKET)
        {
            std::cerr << "Falha ao criar socket\n";
            return false;
        }

        servaddr.sin_family = AF_INET;
        servaddr.sin_port = htons(port);

        if (inet_pton(AF_INET, ip_server.c_str(), &servaddr.sin_addr) <= 0)
        {
            std::cerr << "IP inválido\n";
            closesocket(sockfd);
            return false;
        }

        if (bind(sockfd, (sockaddr *)&servaddr, sizeof(servaddr)) == SOCKET_ERROR)
        {
            std::cerr << "Falha ao bindar socket\n";
            closesocket(sockfd);
            return false;
        }
        savePort(port);
        return true;
    }

    void sendEventServer(Event event)
    {
        const char *msg =
            (event == EU) ? "EU" : (event == SAI) ? "SAI"
                               : (event == SLA)   ? "SLA"
                                                  : "UNKNOWN";

        sendto(sockfd, msg, (int)strlen(msg), 0,
               (sockaddr *)&servaddr, sizeof(servaddr));
    }
    void listenEvents(LuaEngine *eventP)
    {
        char buffer[1024];
        sockaddr_in from{};
        socklen_t fromlen = sizeof(from);

        while (true)
        {
            int bytes = recvfrom(sockfd, buffer, sizeof(buffer) - 1, 0,
                                 (sockaddr *)&from, &fromlen);

            if (bytes > 0)
            {
                buffer[bytes] = '\0';
                std::string msg(buffer);

                
                std::stringstream ss(msg);
                std::string token;
                std::vector<std::string> parts;
                while (std::getline(ss, token, ':'))
                {
                    parts.push_back(token);
                }
                std::string event = parts[0];
                

                if (event == "onPlayerDamage")
                {
                    if (parts.size() == 4)
                    {
                        
                        std::string nome = parts[1];
                        std::string label = parts[2];
                        float dano = std::stof(parts[3]);
                        eventP->onPlayerDamage(nome,dano);
                    }
                    
                }
                else if (event == "SAI")
                {
                }
                else if (event == "SLA")
                {
                }
            }
        }
    }

    void closeConnection()
    {
        if (sockfd != INVALID_SOCKET)
            closesocket(sockfd);

#if defined(_WIN32) || defined(_WIN64)
        WSACleanup();
#endif
    }
};

#endif