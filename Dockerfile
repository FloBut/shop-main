# syntax=docker/dockerfile:1
# acesta este un dockerfile cu care vrem sa creem imaginea

FROM eclipse-temurin:19-jdk-jammy
WORKDIR /app
# copiez din folderul curent din care sunt adica din mvn/ -> tot ce este in interior
# copiez in /app tot intr un mvn
COPY .mvn/ .mvn
# copiez din pm xml tot in app ./
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
