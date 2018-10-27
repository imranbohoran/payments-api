# A REST API for performing Payment operations

## Prerequisites
- Java 8
- Maven
- MongoDB

## How to build from source
This is a maven based project, so to build (compile, test and build the executable jar)
execute: 

`mvn clean install`

Running the above will compile, run all tests and package a jar file in the target directory.

Tests have 2 phases, where all unit tests (repository and rest controller) would run initially and
then a single end-to-end test with all operations will be executed in the integration-test phase of
the maven build.

Running tests that involve MongoDB will run without having the need to run a MongoDB instance locally.
This is done via embedded Mongo included as a dependency in the `pom.xml`. However, if tests need to 
run against a real MongoDB database, all that needs doing is removing/commenting the `de.flapdoodle.embed`
and `cz.jirutka.spring` dependencies in the pom.xml. The tests would then run against a local MongoDB
server.

## How to run the application
The executable jar file will be created when the build from source is performed. And then to run;
`java -jar target/payment-service-1.0-SNAPSHOT.jar`


## Configuring the application
Configuration is minimal or nothing. `application.properties` has the only configuration parameters.
The configuration are around MongoDB. So if MongoDB is not running on port `27017` and the host is
not `localhost`, they need to change in the `application.properties`. Else nothing to do.
 

