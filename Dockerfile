FROM openjdk:8-jre-alpine
WORKDIR /app
ADD ./api/target/api-1.0-SNAPSHOT.jar /app
EXPOSE 9000 
CMD ["java", "-jar", "api-1.0-SNAPSHOT.jar"]
