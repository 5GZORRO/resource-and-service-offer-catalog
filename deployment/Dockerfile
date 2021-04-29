FROM maven:3-openjdk-8 AS build

RUN mkdir /usr/src/offer_catalog
COPY . /usr/src/offer_catalog
WORKDIR /usr/src/offer_catalog
RUN mvn clean package

FROM openjdk:8-jre-alpine
RUN mkdir /offer_catalog
COPY --from=build /usr/src/offer_catalog/offering_catalog/target/offering_catalog-1.0-SNAPSHOT.jar /offer_catalog/
WORKDIR /offer_catalog

EXPOSE ${OFFER_CATALOG_PORT}

CMD java -jar offering_catalog-1.0-SNAPSHOT.jar