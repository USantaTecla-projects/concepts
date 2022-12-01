FROM openjdk:17
EXPOSE 8443
ADD target/concepts.jar concepts.jar
ENTRYPOINT ["java","-jar","--enable-preview","concepts.jar"]


