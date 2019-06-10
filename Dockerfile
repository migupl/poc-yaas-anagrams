FROM openjdk:8-jdk-alpine

RUN apk add --no-cache unzip

WORKDIR /app

COPY build/libs/*.jar server.jar

RUN unzip server.jar && rm -f server.jar

EXPOSE 8080
CMD java -noverify ${JAVA_OPTS}  org.springframework.boot.loader.JarLauncher