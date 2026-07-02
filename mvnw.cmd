@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.

@echo off
setlocal enabledelayedexpansion

set MAVEN_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip
if "%MAVEN_HOME%"=="" set MAVEN_HOME=.\.maven
set MAVEN_BIN=%MAVEN_HOME%\bin\mvn.cmd

if not exist "%MAVEN_BIN%" (
    echo Downloading Maven...
    if not exist "%MAVEN_HOME%" mkdir "%MAVEN_HOME%"
    
    cd /d "%MAVEN_HOME%"

    REM Prefer curl.exe (bundled with Windows 10 1803+ / Windows 11); fall back to PowerShell for older Windows.
    where curl >nul 2>&1
    if !errorlevel! == 0 (
        curl -fsSL -o maven.zip "%MAVEN_URL%"
    ) else (
        powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%MAVEN_URL%', 'maven.zip')"
    )

    REM Prefer tar (bundled with Windows 10 1803+); fall back to PowerShell Expand-Archive.
    where tar >nul 2>&1
    if !errorlevel! == 0 (
        tar -xf maven.zip --strip-components 1
    ) else (
        powershell -Command "Expand-Archive 'maven.zip' -DestinationPath . -Force"
        powershell -Command "Move-Item -Path 'apache-maven-3.9.6\*' -Destination . -Force"
        powershell -Command "Remove-Item 'apache-maven-3.9.6' -Force"
    )

    del /f /q maven.zip
    cd /d "%~dp0"
)

"%MAVEN_BIN%" %*
