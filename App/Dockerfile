FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B || echo "Dependency resolution failed, continuing..."

COPY src src

RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/App-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]