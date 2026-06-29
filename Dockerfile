FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

COPY securetrack/pom.xml .
COPY securetrack/src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/securetrack-0.0.1-SNAPSHOT.jar"]