# CRIAR UM AMBIENTE COM JDK E MAVEN PARA COMPILAR O PROJETO
# ETAPA 1: COMPILAÇÃO
FROM maven:3-eclipse-temurin-25-alpine AS build
# DEFINIR O DIRETÓRIO DE TRABALHO
WORKDIR /app

# Copia apenas o POM para baixar as dependências primeiro (otimização de cache)
COPY pom.xml .
RUN mvn -B dependency:go-offline


# Copia o código-fonte e compila (será muito mais rápido)
# COMPILAR O PROJETO
COPY src ./src
RUN mvn -B clean package -DskipTests

# ETAPA 2: IMAGEM FINAL
# CRIAR A IMAGEM FINAL COM O JAR DO PROJETO
FROM eclipse-temurin:25-jre-alpine
# DEFINIR O DIRETÓRIO DE TRABALHO
WORKDIR /app


# COPIAR O JAR DO PROJETO DA ETAPA DE BUILD
COPY --from=build /app/target/*.jar app.jar

# COMANDO PARA EXECUTAR O JAR DO PROJETO
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]