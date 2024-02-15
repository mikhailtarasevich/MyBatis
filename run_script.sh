#!/bin/bash

mvn clean

if [ $? -eq 0 ]; then
    mvn package

    if [ $? -eq 0 ]; then
        cp testDB.sqlite target/

        cd target

        java -jar MyBatis-1.0-SNAPSHOT.jar
    else
        echo "Ошибка при выполнении mvn package"
    fi
else
    echo "Ошибка при выполнении mvn clean"
fi
