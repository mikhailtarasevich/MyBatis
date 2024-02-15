# "Telda" Test Task - REST API / Spring Boot / MyBatis / Spring Cache

This API is for a Region dictionary where you can perform simple CRUD operations.

## Install and Run the App
### If you're using Maven, you can use:

    mvn install

    cp testDB.sqlite target/

    cd target

    java -jar MyBatis-1.0-SNAPSHOT.jar

### Alternatively, on UNIX-like OS, you can run the script:

    chmod +x run_script.sh

    ./run_script.sh


### If you don't have Java or Maven installed, use Docker:

    docker pull mikhailtarasevich/telda-test-app

    docker run -p 8888:8888 mikhailtarasevich/telda-test-app


## How to Use the Application

Visit the Swagger UI page [http://localhost:8888/swagger-ui/](http://localhost:8888/swagger-ui/)