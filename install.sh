#!/bin/bash

BASE_MF="Manifest-Version: 1.0\nCreated-By: Guillaume Laroyenne\nClass-Path: .";

rm -r out/ 2>/dev/null;
rm *.jar 2>/dev/null;

mkdir out/;

javac src/client/*.java -d out/;
javac src/server/*.java -d out/;

# client JAR
echo -e "$BASE_MF" > out/client/MANIFEST.MF;
echo "Main-Class: client.App" >> out/client/MANIFEST.MF;
cd out/;
jar cvmf client/MANIFEST.MF ../client.jar client/*.class;
cd ..;

# server JAR
echo -e "$BASE_MF" > out/server/MANIFEST.MF;
echo "Main-Class: server.App" >> out/server/MANIFEST.MF;
cd out;
jar cvmf server/MANIFEST.MF ../server.jar server/*.class;
cd ..;

rm -r out/;

exit 0;