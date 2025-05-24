# Use a imagem do JDK 21
FROM eclipse-temurin:21-jre-alpine

# Copia o jar para dentro do container
COPY target/backend-test-java-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta da aplicação
EXPOSE 8080

# Comando para rodar o jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
