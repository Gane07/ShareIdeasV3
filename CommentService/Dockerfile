# Use Java 17 base image
FROM openjdk:17-alpine
 
#working dir
WORKDIR /usr/src
 
#moving
ADD target/CommentServiceFile.jar /usr/src/CommentServiceFile.jar
 
# Expose service port
EXPOSE 8084
 
#run
ENTRYPOINT ["java", "-jar", "/usr/src/CommentServiceFile.jar"]