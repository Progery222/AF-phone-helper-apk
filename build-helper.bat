@echo off
set JAVA_HOME=C:\Program Files\Microsoft\jdk-17.0.19.10-hotspot
set ANDROID_HOME=C:\Users\Mobile Farm\AppData\Local\Android\Sdk
cd /d "%~dp0"
call gradlew.bat %*
