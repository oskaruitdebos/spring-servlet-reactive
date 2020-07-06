FROM openjdk:14-alpine

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]