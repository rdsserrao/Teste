FROM openjdk:15.0.1-oraclelinux8
COPY . .
CMD ["java","-jar","/target/book-0.0.1-SNAPSHOT.jar"]
