# FROM eclipse-temurin:17
FROM ibm-semeru-runtimes:open-17-jre-focal
VOLUME /tmp
ARG JAR_FILE=/backend/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-XX:+UseSerialGC", "-Xss512k", "-XX:MaxRAM=200m"]