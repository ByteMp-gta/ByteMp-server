cmake_minimum_required(VERSION 3.15)
project(luacpp)

set(CMAKE_CXX_STANDARD 17)


file(GLOB LUA_SRC lua/*.c)
list(REMOVE_ITEM LUA_SRC ${CMAKE_CURRENT_SOURCE_DIR}/lua/onelua.c)


set(CPP_MAIN_SRC 
    main.cpp
    cpp/LuaEngine.cpp
    network/connection.hpp
)


add_executable(luacpp
    ${CPP_MAIN_SRC}
    ${LUA_SRC}
)


target_include_directories(luacpp PRIVATE
    lua
    cpp
    network
)


if (WIN32)
    target_compile_definitions(luacpp PRIVATE _CRT_SECURE_NO_WARNINGS)
    target_link_libraries(luacpp ws2_32)
endif()


if (UNIX)
    target_link_libraries(luacpp m)
endif()