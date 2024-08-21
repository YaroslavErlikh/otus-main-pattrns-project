FROM amazoncorretto:17-alpine-jdk
COPY target/service.jar service.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -jar service.jar
