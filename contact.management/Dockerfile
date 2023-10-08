FROM penjdk:8-jdk-alpine

WORKDIR /app

COPY build/libs/contact.management-0.0.1-SNAPSHOT.jar contact.management-0.0.1-SNAPSHOT.jar

RUN wget -N https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip
RUN unzip newrelic-java.zip -d /app

EXPOSE 8080

ENTRYPOINT ["java","-javaagent:newrelic/newrelic.jar","contact.management-0.0.1-SNAPSHOT.jar"]
