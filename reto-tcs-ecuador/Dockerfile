FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/reto-tcs-ecuador-0.0.1.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "app.jar"]
