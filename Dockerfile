FROM openjdk:17-oracle

VOLUME /tmp

EXPOSE 8081

ADD /target/SpringConditional-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]