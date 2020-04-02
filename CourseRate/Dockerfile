FROM openjdk:8
RUN mkdir course
COPY target/CourseRate-0.1.0.jar /course/course.jar
WORKDIR /course
CMD ["java", "-jar", "course.jar"]