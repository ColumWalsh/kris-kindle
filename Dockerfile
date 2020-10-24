FROM openjdk:14-alpine
COPY target/kriskindle-*.jar kriskindle.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "kriskindle.jar"]