# DOCKER FILE
FROM openjdk:17-jdk
LABEL maintainer="milgam1239@gmail.com"
ARG JAR_FILE=build/libs/SwipingJeju-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-jar","/docker-springboot.jar"]