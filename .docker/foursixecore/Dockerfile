FROM gradle:8.7-jdk17 AS build
WORKDIR /workspace

# Optional: disable unneeded modules
ENV DISABLE_APP=true
ENV DISABLE_FRONTEND=true
ENV DISABLE_PRESENTER=true
ENV DISABLE_SCALER=true

# Optional: avoid Gradle daemon inside containers
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

# Copy only files necessary for dependency caching
COPY settings.gradle.kts build.gradle.kts gradle.properties gradlew ./
COPY gradle/ gradle/
COPY build-logic/ build-logic/
COPY foursixcore/build.gradle.kts foursixcore/

# Pre-download dependencies (massive speed boost)
RUN chmod +x ./gradlew && ./gradlew -p foursixcore dependencies

# Copy full project (source code changes won't bust dependency cache)
COPY . .

# Optional: make gradlew executable again after full copy
RUN chmod +x ./gradlew
