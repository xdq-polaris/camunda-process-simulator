FROM openjdk:17-jdk
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
ARG JAR_FILE=build/libs/camunda-process-simulator-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]