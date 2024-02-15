FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
ARG DB_FILE=testDB.sqlite
COPY ${JAR_FILE} app.jar
COPY ${DB_FILE} testDB.sqlite
ENTRYPOINT ["java","-jar","/app.jar"]