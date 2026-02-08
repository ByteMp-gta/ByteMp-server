#include "LuaEngine.h"
#include <iostream>
#include <cstring>

static LuaEngine *s_instance = nullptr;

extern "C" int lua_addEvent(lua_State *L)
{
    const char *name = luaL_checkstring(L, 1);

    luaL_checktype(L, 2, LUA_TFUNCTION);

    lua_pushvalue(L, 2);
    int ref = luaL_ref(L, LUA_REGISTRYINDEX);
    if (s_instance)
    {
        s_instance->registerCallback(std::string(name), ref);
    }
    return 0;
}
LuaEngine::LuaEngine()
{
    L = luaL_newstate();
    luaL_openlibs(L);
    s_instance = this;

    lua_register(L, "addEvent", lua_addEvent);
}

void LuaEngine::registerCallback(const std::string &name, int ref)
{
    callbacks[name] = ref;
}

bool LuaEngine::runScript(const std::string &path)
{
    if (luaL_dofile(L, path.c_str()) != LUA_OK)
    {
        std::cerr << "Erro ao executar script: " << lua_tostring(L, -1) << std::endl;
        lua_pop(L, 1);
        return false;
    }
    return true;
}

LuaEngine::~LuaEngine()
{
    lua_close(L);
}
/*
void LuaEngine::triggerEvent(const char* str, const std::string& player) {
    auto it = callbacks.find(str);
    if (it != callbacks.end()) {
        lua_rawgeti(L, LUA_REGISTRYINDEX, it->second);
        lua_pushstring(L, player.c_str());
        if (lua_pcall(L, 1, 0, 0) != LUA_OK) {
            std::cerr << "Erro ao chamar callback: " << lua_tostring(L, -1) << std::endl;
            lua_pop(L, 1);
        }
    }
}
*/
void LuaEngine::onPlayerDamage(const std::string &player, const float damage)
{
    auto it = callbacks.find("onPlayerDamage");
    if (it != callbacks.end())
    {
        lua_rawgeti(L, LUA_REGISTRYINDEX, it->second);
        lua_pushstring(L, player.c_str());
        lua_pushnumber(L, damage);
        if (lua_pcall(L, 2, 0, 0) != LUA_OK)
        {
            std::cerr << "Erro ao chamar callback: " << lua_tostring(L, -1) << std::endl;
            lua_pop(L, 1);
        }
    }
}
