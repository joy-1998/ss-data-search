FROM azul/zulu-openjdk-alpine:17-jre

WORKDIR /app

COPY target/*.jar search.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "search.jar"]