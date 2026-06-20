FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar todo el código fuente
COPY . .

# Construir el proyecto dentro del contenedor
RUN ./mvnw clean package -DskipTests

# Mover el jar generado
RUN cp target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]