FROM openjdk:16-jdk-alpine
COPY target/*.jar /app/book-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/app/book-0.0.1-SNAPSHOT.jar"]