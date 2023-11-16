FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY ./build/libs/backend-0.0.1.jar /app/backend.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "backend.jar"]
