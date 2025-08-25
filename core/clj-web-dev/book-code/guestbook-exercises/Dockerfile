FROM openjdk:11-buster

COPY target/uberjar/guestbook.jar /guestbook/app.jar

COPY prod-config.edn /guestbook/config.edn

EXPOSE 3000

CMD ["java", "-jar", "-Dconf=/guestbook/config.edn", "/guestbook/app.jar"]
