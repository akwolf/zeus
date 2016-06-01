#!/bin/sh

if [ ! -d "node_modules" ]; then
    npm install
fi

gulp deploy

if [ ! -x "./gradlew" ]; then
    chmod +x ./gradlew
fi

./gradlew bootRepackage

/etc/init.d/zeusd stop

rm -rf /usr/local/tomcat/zeus/webapps/ROOT*
cp ./build/libs/ROOT.war /usr/local/tomcat/zeus/webapps/ROOT.war

/etc/init.d/zeusd start