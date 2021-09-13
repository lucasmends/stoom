FROM openjdk:11 as build

WORKDIR usr/src/app
COPY . ./
RUN ./mvnw clean package

FROM openjdk:11

COPY --from=build /usr/src/app/target/backend-qualification.jar ./
EXPOSE 8080

CMD ["java", "-jar", "backend-qualification.jar"]