package com.example.demo.weatherHandle;

import com.example.demo.dataToFront.Weather;
import com.example.demo.repos.*;
import com.example.demo.tables.*;
import com.example.demo.weatherHandle.email.HtmlMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class WeatherScheduler {     // Класс отправляет запрос в Погоду каждый час
    @Autowired
    private tblLocationUserRelationsRepo LocationUserRelationsRepo;
    @Autowired
    private tblUsersRepo UsersRepo;
    @Autowired
    private tblLocationsRepo LocationsRepo;
    @Autowired
    private tblCustomTriggersRepo CustomTriggersRepo;
    @Autowired
    private tblDefaultTriggersRepo DefaultTriggersRepo;

    @Autowired
    private tblAutoRequestHistoryRepo AutoRequestHistoryRepo;
    @Autowired
    private tblReplyWeatherRepo ReplyWeatherRepo;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Формат времени
    DateFormat dateFormatEmailHours = new SimpleDateFormat("HH:mm");
    DateFormat getDateFormatEmailYear = new SimpleDateFormat("dd.MM.yyyy");
    private final WeatherApiClient weatherApiClient; // Хранит ответ
    @Autowired
    HtmlMessageService emailService; // Отправляет письма


    public WeatherScheduler(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;

    }

    //@Scheduled(cron = "0 0 * * * *") // Чтобы запускать каждый час
    @Scheduled(cron = "0 * * * * *") // Чтобы запускать каждую минуту
    public void fetchWeatherAndStore() throws JsonProcessingException,
            ParseException {

        List<tblLocations> allLocations = (List<tblLocations>) LocationsRepo.findAll();
        for (tblLocations location : allLocations) {

            String respTime = dateFormat.format(new Date());    // Время отправки запроса в Погоду
            String respTimeEmailHours = dateFormatEmailHours.format(new Date());
            String respTimeEmailYear = getDateFormatEmailYear.format(new Date());

            System.out.println(respTime + " Проверка климата ---------- " + location.getLocation_name());

            Weather weatherResponse = weatherApiClient.getWeather(location.getLat(), location.getLon()); // Получили ответ

            int temperature = weatherResponse.getCelsius(); // Парсим ответ
            String condition = weatherResponse.getCondition();
            Double windSpeed = weatherResponse.getWind_speed();
            int humidity = weatherResponse.getHumidity();

            String reply_time = dateFormat.format(new Date());

            tblReplyWeather weather = new tblReplyWeather(); // Сохранили в базу данных статистику
            weather.setCelsius((byte) temperature);
            weather.setHumidity(humidity);
            weather.setCondition(condition);
            weather.setWind_speed(windSpeed);
            ReplyWeatherRepo.save(weather);

            tblAutoRequestHistory history = new tblAutoRequestHistory();
            history.setLocationWeather(location);
            history.setReply_weatherID(weather);
            history.setResp_time(Timestamp.valueOf(respTime));
            history.setReply_time(Timestamp.valueOf(reply_time));
            AutoRequestHistoryRepo.save(history);


            tblCustomTriggers customTrigger = location.getCustom_triggerID(); // Проверка тригерных значений


            String messageCustom = "";
            if (temperature < customTrigger.getCelsius_min()) {
                messageCustom += "Температура ниже нормы: " + temperature + "℃<br>";
            } else if (temperature > customTrigger.getCelsius_max()) {
                messageCustom += "Температура выше нормы: " + temperature + "℃<br>";
            }
            if (humidity < customTrigger.getHumidity_min()) {
                messageCustom += "Влажность ниже нормы: " + humidity + "%<br>";
            } else if (humidity > customTrigger.getHumidity_max()) {
                messageCustom += "Влажность выше нормы: " + humidity + "%<br>";
            }
            if (windSpeed < customTrigger.getWind_speed_min()) {
                messageCustom += "Скорость ветра ниже нормы = " + windSpeed + "м/c<br>";
            } else if (windSpeed > customTrigger.getWind_speed_max()) {
                messageCustom += "Скорость ветра выше нормы = " + windSpeed + "м/c<br>";
            }


            List<tblCustomConditions> allCustomConditions = customTrigger.getConditions();
            for (tblCustomConditions customCondition : allCustomConditions) {
                if (Objects.equals(customCondition.getCondition(), condition)) {
                    messageCustom += "Климат вне нормы: " + condition + "<br>";
                }
            }

            if (messageCustom != "") { // отправка писем
                List<tblLocationUserRelations> allLocationUserRelations = (List<tblLocationUserRelations>) LocationUserRelationsRepo.findAll();
                for (tblLocationUserRelations locationUserRelation : allLocationUserRelations) {
                    if (Objects.equals(locationUserRelation.getLocations().getLocation_id(), location.getLocation_id())) {

                        try {
                            emailService.sendEmail(locationUserRelation.getUserId().getEmail(), location.getLocation_name(), respTimeEmailHours, respTimeEmailYear, messageCustom);
                        } catch (MailException mailException) {
                            System.out.println("Ошибка во время отправки сообщения");
                        } catch (MessagingException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                System.out.println("Климат вне нормы. Сообщения разосланы.");
            } else {
                System.out.println("Климат в норме");
            }
        }
    }
}
