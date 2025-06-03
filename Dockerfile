FROM eclipse-temurin:17-jdk
COPY target/scratch-game.jar app.jar
# to copy config file to container image
COPY src/test/resources/config-3x3.json config.json

ENTRYPOINT ["java", "-jar", "app.jar"]