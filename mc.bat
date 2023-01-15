rem @echo off
title Minecraft Launcher

:: set username
set /p player=What username would you like?

:: main directory of the game
set MC_DIR=%APPDATA%\.minecraft

:: Are stored here...
:: saves/, resourcepacks/ and screenshots/ dirs
:: and options.txt and servers.dat files
set GAME_DIR=%MC_DIR%

:: libraries and resource dir (requires no changes)
set ASSETS_DIR=%MC_DIR%\assets
set ASSETS_INDEX=1.16

:: version minecraft (from versions/ dir)
set GAME_VERSION=1.16.5

:: get archive from natives/ dir
set NATIVES_DIR=%MC_DIR%\bin\%GAME_VERSION%

:: *.jar paths generated by make_libs_list.py
set LIBRARIES_LIST=

:: player nickname (the over head)
set PLAYER_NAME=%player%

:: game window resolution
set WINDOW_W=800
set WINDOW_H=640

:: java machine arguments
set "JVM_RAM=-Xmx2G"
set "JVM_ARGS=-XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M"

:: running minecraft
cd /d %MC_DIR%

java.exe %JVM_RAM% %JVM_ARGS% ^
-Djava.library.path=%NATIVES_DIR% ^
-cp %LIBRARIES_LIST% ^
net.minecraft.client.main.Main ^
--username %PLAYER_NAME% ^
--version %GAME_VERSION% ^
--accessToken 0 --userProperties {} ^
--gameDir %GAME_DIR% ^
--assetsDir %ASSETS_DIR% ^
--assetIndex %ASSETS_INDEX% ^
--width %WINDOW_W% ^
--height %WINDOW_H%