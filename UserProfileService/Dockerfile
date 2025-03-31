# Use Java 17 base image
FROM openjdk:17-alpine
 
#working dir
WORKDIR /usr/src
 
#moving
ADD target/UserProfileServiceFile.jar /usr/src/UserProfileServiceFile.jar
 
# Expose service port
EXPOSE 8082
 
#run
ENTRYPOINT ["java", "-jar", "/usr/src/UserProfileServiceFile.jar"]