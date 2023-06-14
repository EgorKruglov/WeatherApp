package com.example.demo.weatherHandle;

import com.example.demo.dataToFront.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherApiClient {     // Класс с методом, кидающим запрос в Погоду

    private final WebClient webClient;

    public WeatherApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl("")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Yandex-API-Key", "8a13c6a3-f2b0-4632-a67c-e755248b2c3e")
                .build();
    }

    public Weather getWeather(double lat, double lon) throws JsonProcessingException {
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon;
        String weatherResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Weather weather = new Weather();
        ObjectMapper objectMapper = new ObjectMapper(); // Для парсинга ответа
        JsonNode rootNode = objectMapper.readTree(weatherResponse); // Парсим ответ
        JsonNode factNode = rootNode.path("fact");
        byte temperature = (byte) factNode.path("temp").asInt();
        String condition = factNode.path("condition").asText();
        Double windSpeed = factNode.path("wind_speed").asDouble();
        int humidity = factNode.path("humidity").asInt();
        weather.setCelsius(temperature);
        weather.setCondition(condition);
        weather.setHumidity(humidity);
        weather.setWind_speed(windSpeed);
        return weather;
    }

    public List<Weather> getWeatherSeven(double lat, double lon) throws JsonProcessingException, ParseException {
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + lat + "&lon=" + lon;
        String weatherResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        List<Weather> weather = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper(); // Для парсинга ответа
        JsonNode rootNode = objectMapper.readTree(weatherResponse); // Парсим ответ
        ArrayNode forecastsNode = (ArrayNode) rootNode.get("forecasts");

        int i = 0;
        for (JsonNode forecasts : forecastsNode ){
            if (i<7){
                Weather dayWeather = new Weather();

                dayWeather.setDate(String.valueOf(forecasts.path("date")).replaceAll("\"", ""));
                JsonNode partNode = forecasts.path("parts").path("day_short");
                dayWeather.setCelsius((byte) partNode.path("temp").asInt());
                dayWeather.setCondition(partNode.path("condition").asText());
                dayWeather.setWind_speed(partNode.path("wind_speed").asDouble());
                dayWeather.setHumidity(partNode.path("humidity").asInt());

                weather.add(dayWeather);

                i++;}else {break;}
        }


        return weather;
    }
}
