#!/bin/sh
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

# This script is a simple wrapper for Maven Wrapper.
# It downloads and runs Maven if it's not already available.

set -e

MAVEN_URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.tar.gz"
MAVEN_HOME="${MAVEN_HOME:-.maven}"
MAVEN_BIN="${MAVEN_HOME}/bin/mvn"

if [ ! -f "$MAVEN_BIN" ]; then
    echo "Downloading Maven..."
    mkdir -p "$MAVEN_HOME"
    cd "$MAVEN_HOME"
    if command -v curl >/dev/null 2>&1; then
        curl -fsSL "$MAVEN_URL" -o maven.tar.gz
    elif command -v wget >/dev/null 2>&1; then
        wget -q "$MAVEN_URL" -O maven.tar.gz
    else
        echo "Error: neither curl nor wget is installed; cannot download Maven." >&2
        exit 1
    fi
    tar xzf maven.tar.gz --strip-components 1
    rm maven.tar.gz
    cd -
fi

"$MAVEN_BIN" "$@"
