# Estágio 1: Build da aplicação usando Maven com suporte a Java 25
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências (otimiza o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o arquivo .jar final ignorando os testes de build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Ambiente leve de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Adiciona um usuário sem privilégios administrativos por boas práticas de segurança
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta simbólica (lembrando que no yml usamos port: 0 para ser dinâmica)
EXPOSE 0

# Executa o microserviço otimizando o uso de memória em containers
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "app.jar"]
