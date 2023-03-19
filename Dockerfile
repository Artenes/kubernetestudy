FROM amazoncorretto:17.0.6-al2
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY build/libs/anotes-*-SNAPSHOT.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar