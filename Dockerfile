FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} main-warehouse.jar
ENTRYPOINT ["java","-jar","/main-warehouse.jar"]