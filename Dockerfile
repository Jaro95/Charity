FROM openjdk:17-jdk

COPY target/charity.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar", "charity.jar"]