FROM openjdk:17
EXPOSE 80
ADD target/yandex-spring-boot-docker.jar yandex-spring-boot-docker.jar
ENTRYPOINT ["java","-jar","yandex-spring-boot-docker.jar"]