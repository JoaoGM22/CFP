FROM amazoncorretto:23-alpine-jdk

# Define o diretório de trabalho
WORKDIR /cfp

# Monta o volume temporário
VOLUME /tmp

# Define a variável de ambiente JAVA_OPTS
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

# Copia o JAR com as dependências para o contêiner
COPY target/cfp-0.0.1-SNAPSHOT.jar /cfp/cfp.jar

# Copia os templates do Spring Boot para o diretório adequado
COPY src/main/resources/templates /app/templates

# Expõe a porta para o Spring Boot
EXPOSE 8080

# Comando de execução para iniciar o Spring Boot via JAR
ENTRYPOINT ["java", "-jar", "/cfp/cfp.jar"]

