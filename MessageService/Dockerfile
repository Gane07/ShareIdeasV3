# Use Java 17 base image
FROM openjdk:17-alpine
 
#working dir
WORKDIR /usr/src
 
#moving
ADD target/MessageServiceFile.jar /usr/src/MessageServiceFile.jar
 
# Expose service port
EXPOSE 8085
 
#run
ENTRYPOINT ["java", "-jar", "/usr/src/MessageServiceFile.jar"]