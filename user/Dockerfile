FROM amazoncorretto:17-alpine-jdk
COPY service/target/user-service.jar user-service.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -jar user-service.jar
