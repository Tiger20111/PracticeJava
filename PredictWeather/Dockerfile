FROM openjdk:8
RUN mkdir predict
COPY target/PredictWeather-0.1.0.jar /predict
WORKDIR /weather
CMD ["java", "-jar", "PredictWeather-0.1.0.jar"]