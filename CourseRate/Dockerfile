FROM openjdk:8
RUN mkdir course
COPY target/CourseRate-0.1.0.jar /course
WORKDIR /course
CMD ["java", "-jar", "CourseRate-0.1.0.jar"]