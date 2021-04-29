# tmf-offering-catalog
Implementation of the TM Forum Product Catalog Management APIs for a VNF/PNF offering catalog.
If you want to install the Offering Catalog using docker-compose skip the Requirements, and the first Installation
sections. </br>

## Requirements
- Java 8 </br>
  ```bash
  sudo apt update
  sudo apt install openjdk-8-jdk
  ```
- Maven </br>
  ```bash
  sudo apt update
  sudo apt install maven
  ```
- PostgreSQL </br>
  ```bash
  docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres
  ```
## Installation
Customize your Offering Catalog properties in ```offering_catalog/src/main/resources/application-local.properties``` then from
```offering_catalog/``` run the following commands.
```bash
export spring_profiles_active=local
mvn clean install
java -jar offering_catalog/target/offering_catalog-1.0-SNAPSHOT.jar
```

## Installation [docker-compose]
Customize your Offering Catalog properties in ```offering_catalog/deployment/.env``` then from ```offering_catalog/``` 
run the following command.
```bash
docker-compose -f deployment/docker-compose.yaml up -d
```

## Usage
Browsing `http://localhost:8080/tmf-api/` you can access the swagger documentation and test the REST APIs. 