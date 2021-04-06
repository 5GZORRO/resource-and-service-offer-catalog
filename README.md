# tmf-offering-catalog
Implementation of the TM Forum Product Catalog Management APIs for a VNF/PNF offering catalog

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
- Apache Kafka
  ```bash
  git clone https://github.com/wurstmeister/kafka-docker
  ```
  Change `docker-compose.yaml` environment as shown below.
  ```bash
  KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:9093,OUTSIDE://localhost:9092
  KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INSIDE:PLAINTEXT,OUTSIDE:PLAINTEXT
  KAFKA_LISTENERS: INSIDE://0.0.0.0:9093,OUTSIDE://0.0.0.0:9092
  KAFKA_INTER_BROKER_LISTENER_NAME: INSIDE
  KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
  ```
  Run:
  ```bash
  docker-compose up -d
  ```
## Installation
```bash
mvn clean install
java -jar offering_catalog/target/offering_catalog-1.0-SNAPSHOT.jar
```

## Usage
Browsing `http://localhost:8080/tmf-api/` you can access the swagger documentation and test the REST APIs. 