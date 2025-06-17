# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("siembrahora")

add_library(${CMAKE_PROJECT_NAME} SHARED
        native-lib.cpp
        TelemetryManager.cpp
)

# --- ADD THIS NEW BLOCK ---
# This command tells the compiler to look for header files (.h)
# in the current directory (the 'cpp' folder). This makes your #include
# directives more reliable.
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}
)
# --- END OF NEW BLOCK ---

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log)