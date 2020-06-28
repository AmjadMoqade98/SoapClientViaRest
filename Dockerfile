FROM openjdk:8
ADD  build/libs/SoapClientViaRest-0.0.1-SNAPSHOT.jar   SoapClientViaRest-0.0.1-SNAPSHOT.jar
EXPOSE 8099
ENTRYPOINT ["java" , "-jar" , "SoapClientViaRest-0.0.1-SNAPSHOT.jar"]