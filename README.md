# Dockerize Spring

## spring packing

```$xslt
mvn clean package
```

## .dockerignore

```$xslt
.*
target/*
!target/demo-*.jar
```

## Dockerfile

```$xslt
FROM openjdk:8-jdk-alpine

RUN mkdir -p /app

ADD target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]
```

## build Docker 

```$xslt
docker build -t unclebae/demo-api .
```

## run docker

```$xslt
docker run -d -p 8088:8080 unclebae/demo-api
```

## test

```$xslt
localhost:8088/api/greeting
```