FROM openjdk:8-jdk
VOLUME /tmp
COPY ./target/promedService-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]