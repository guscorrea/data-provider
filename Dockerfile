FROM openjdk:8-jre-alpine
COPY target/data-provider-1.0.0.jar data-provider-1.0.0.jar
ENTRYPOINT ["java","-jar","/data-provider-1.0.0.jar"]