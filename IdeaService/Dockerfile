# Use Java 17 base image
FROM openjdk:17-alpine
 
#working dir
WORKDIR /usr/src
 
#moving
ADD target/IdeaServiceFile.jar /usr/src/IdeaServiceFile.jar
 
# Expose service port
EXPOSE 8083
 
#run
ENTRYPOINT ["java", "-jar", "/usr/src/IdeaServiceFile.jar"]