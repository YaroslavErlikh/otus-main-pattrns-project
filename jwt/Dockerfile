FROM amazoncorretto:17-alpine-jdk
COPY service/target/jwt-service.jar jwt-service.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -jar jwt-service.jar
