package com.example.demo.weatherHandle;

import com.example.demo.dataToFront.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.demo.repos.*;
import com.example.demo.tables.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Component
public class WeatherScheduler {     // Класс кидает запрос в Погоду каждый час
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
    @Autowired
    HtmlMessageService emailService;
    private final WeatherApiClient weatherApiClient; // Хранит ответ


    public WeatherScheduler(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;

    }

    //@Scheduled(cron = "0 0 * * * *") // Чтобы запускать каждый час
    //@Scheduled(cron = "0 * * * * *") // Чтобы запускать каждую минуту
    public void fetchWeatherAndStore() throws JsonProcessingException, ParseException {

        List<tblLocations> allLocations = (List<tblLocations>) LocationsRepo.findAll();
        for (tblLocations location : allLocations) {
            System.out.println("------------------LOCATION:   " + location.getLocation_name());

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String resp_time = dateFormat.format(date);

            Weather weatherResponse = weatherApiClient.getWeather(location.getLat(), location.getLon()); // Получили ответ

            int temperature = weatherResponse.getCelsius();
            String condition = weatherResponse.getCondition();
            Double windSpeed = weatherResponse.getWind_speed();
            int humidity = weatherResponse.getHumidity();

            Date date_for_reply = new Date();
            String reply_time = dateFormat.format(date_for_reply);

            System.out.println(reply_time + "  temperature =  " + temperature + ",   condition = " + condition + ",   windSpeed = " + windSpeed + ",   humidity = " + humidity); // Вот оно


            tblReplyWeather weather = new tblReplyWeather();
            weather.setCelsius((byte) temperature);
            weather.setHumidity(humidity);
            weather.setCondition(condition);
            weather.setWind_speed(windSpeed);

            weather = ReplyWeatherRepo.save(weather);

            tblAutoRequestHistory history =  new tblAutoRequestHistory();
            history.setLocationWeather(location);
            history.setReply_weatherID(weather);
            history.setResp_time(Timestamp.valueOf(resp_time));
            history.setReply_time(Timestamp.valueOf(reply_time));

            history = AutoRequestHistoryRepo.save(history);


            tblCustomTriggers customTrigger = location.getCustom_triggerID();


            String messageCustom = "";
            if (temperature < customTrigger.getCelsius_min() || temperature > customTrigger.getCelsius_max()){
                messageCustom += "Температура(℃) = " + temperature + "\n";
            }
            if (humidity < customTrigger.getHumidity_min() || humidity > customTrigger.getHumidity_max()){
                messageCustom += "Влажность(%) = " + humidity + "\n";
            }
            if (windSpeed < customTrigger.getWind_speed_min() || windSpeed > customTrigger.getWind_speed_max()){
                messageCustom += "Скорость ветра(с/м) = " + windSpeed + "\n";
            }




            List<tblCustomConditions> allCustomConditions = customTrigger.getConditions();
            for (tblCustomConditions customCondition : allCustomConditions){
                if (Objects.equals(customCondition.getCondition(), condition)){
                        messageCustom += "Состояние погоды: " + condition + "\n";
                }
            }




            if (messageCustom != ""){
                List<tblLocationUserRelations> allLocationUserRelations = (List<tblLocationUserRelations>) LocationUserRelationsRepo.findAll();
                for (tblLocationUserRelations locationUserRelation : allLocationUserRelations){
                    if (Objects.equals(locationUserRelation.getLocations().getLocation_id(), location.getLocation_id()) ){
                        try { // отправка писем
                            emailService.sendEmail(locationUserRelation.getUserId().getEmail(), location.getLocation_name(), messageCustom);
                            System.out.println(locationUserRelation.getUserId().getEmail());
                        } catch (MailException mailException) {
                            System.out.println("Error while sending out email..{}" + Arrays.toString(mailException.getStackTrace()));
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }


        }
    }
}
