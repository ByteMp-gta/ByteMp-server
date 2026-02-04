#pragma once
#include <map>
#include <string>
extern "C" {
#include "../lua/lua.h"
#include "../lua/lauxlib.h"
#include "../lua/lualib.h"
}

struct lua_State; 
using lua_CFunction = int(*)(lua_State* L);


class LuaEngine {
public:
    LuaEngine();
    ~LuaEngine();

    void addEvent(const char* str);
    void registerCallback(const std::string& name, int ref);
    bool runScript(const std::string& path);
    void triggerEvent(const char* str, const std::string& player);
private:
    lua_State* L;
    std::map<std::string, int> callbacks;
};
