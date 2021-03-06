FROM openjdk:15.0.1-oraclelinux8
COPY /home/jenkins/workspace/Teste_Jenkins_Sonar/target /tmp
WORKDIR /tmp
EXPOSE 6000
CMD ["java","-jar","book-0.0.1-SNAPSHOT.jar"]
