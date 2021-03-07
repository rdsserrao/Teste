FROM openjdk:16-jdk-alpine
COPY target/*.jar /app/movies.jar
CMD ["java","-jar","/app/movies.jar"]