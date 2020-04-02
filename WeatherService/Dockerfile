FROM openjdk:8
RUN mkdir weather
COPY target/WeatherService-0.1.0.jar /weather/weather.jar
WORKDIR /weather
CMD ["java", "-jar", "weather.jar"]