FROM openjdk:8
RUN mkdir weather
COPY target/WeatherService-0.1.0.jar /weather
WORKDIR /weather
CMD ["java", "-jar", "WeatherService-0.1.0.jar"]