FROM openjdk:8
RUN mkdir predict
COPY target/PredictWeather-0.1.0.jar /predict/predict.jar
WORKDIR /predict
RUN chmod u+x predict.jar
CMD ["java", "-jar", "-Dspring.config.location = application.properties", "predict.jar"]