@echo off
gradlew build && copy "build\libs\yugo-gun-0.8.0-1.16.5.jar" "d:\.minecraft\mods\yugogun.jar" /y
