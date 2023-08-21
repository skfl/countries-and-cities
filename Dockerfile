FROM maven:3.9.3-eclipse-temurin-17-alpine as BUILD
RUN mkdir "/sources"
COPY . /sources
WORKDIR /sources
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN mkdir /app
COPY --from=BUILD /sources/target/countries-and-cities.jar /app/countries-and-cities.jar
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser
EXPOSE 8081
ENV POSTGRES_HOST $POSTGRES_HOST
ENV MINIO_HOST $MINIO_HOST
ENV KEYCLOAK_HOST $KEYCLOAK_HOST
ENTRYPOINT "java" "-jar" "countries-and-cities.jar"





