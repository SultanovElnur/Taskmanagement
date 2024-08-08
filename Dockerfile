FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/task-management-system.jar task-management-system.jar
ENTRYPOINT ["java", "-jar", "/task-management-system.jar"]