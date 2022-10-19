FROM amazoncorretto:11
ARG JAR_FILE=build/libs/backend-1.0.jar
COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]
# => 설정파일을 분리해서 사용할 때
# java -jar -Dspring.profiles.active=prod app.jar

