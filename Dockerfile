FROM alpine:latest as build


RUN apk update
RUN apk add openjdk17


COPY . .


RUN chmod +x ./gradlew


RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:17-jre-alpine


EXPOSE 8080


COPY --from=build ./build/libs/ExamenMercado-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]-Xms256m", "-jar", "app.jar"]