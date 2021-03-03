# tmf-offering-catalog
Implementation of the TM Forum Product Catalog Management API for a VNF/PNF offering catalog

<hr>

## Requirements
- PostgreSQL </br> 
  ```bash
  docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres
  ```
- Java 8 </br>
  ```bash
  sudo apt update
  sudo apt install openjdk-8-jdk
  ```
  
## Installation
```bash
mvn clean install
cd offering_catalog
cd target
nohup java -jar offering_catalog-1.0-SNAPSHOT.jar
```

## Usage
Browsing `http://localhost/tmf-api/` you can access the swagger documentation and test the REST APIs. 