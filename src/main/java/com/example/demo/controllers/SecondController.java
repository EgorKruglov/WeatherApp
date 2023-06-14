package com.example.demo.controllers;

import com.example.demo.dataToFront.LocationToFront;
import com.example.demo.repos.*;
import com.example.demo.tables.tblCustomConditions;
import com.example.demo.tables.tblCustomTriggers;
import com.example.demo.tables.tblLocations;
import com.example.demo.tables.tblUsers;
import com.example.demo.weatherHandle.WeatherApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller // This means that this class is a Controller

public class SecondController {
    @Autowired
    private tblLocationUserRelationsRepo LocationUserRelationsRepo;
    @Autowired
    private tblUsersRepo UsersRepo;
    @Autowired
    private tblAdminCodesRepo AdminCodesRepo;
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
    private tblCustomConditionsRepo CustomConditionsRepo;
    private final WeatherApiClient weatherApiClient;
    public SecondController(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<tblCustomTriggers> getAll() {
        // This returns a JSON or XML with the users
        return CustomTriggersRepo.findAll();
    }

   @PostMapping(path = "/test")
    public @ResponseBody ResponseEntity<?> ChangePassword(
            @RequestParam String pass
   ){
        Optional<tblUsers> jack = UsersRepo.findById(3);
        if (jack.isPresent()){
            tblUsers user = jack.get();
            user.setPassword(pass);
            UsersRepo.save(user);
            return new ResponseEntity<>(user.getPassword(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Fail", HttpStatus.CONFLICT);
   }

   @RequestMapping(path = "/index")
     public String welcome(){
        return "index";
   }
    @RequestMapping(path = "/ProfilePage")
    public String welcome2(){
        return "ProfilePage";
    }
    @RequestMapping(path = "/RegistrationPage")
    public String welcome3(){
        return "RegistrationPage";
    }
    @RequestMapping(path = "/UserPage")
    public String welcome4(){
        return "UserPage";
    }
    @RequestMapping(path = "/UserSearchPage")
    public String welcome5(){
        return "UserSearchPage";
    }
    @RequestMapping(path = "/WeatherPage")
    public String welcome6(){
        return "WeatherPage";
    }

    @GetMapping(path = "/get/locations")
    public @ResponseBody ResponseEntity<?> locationsTofront(){
        List<tblLocations> all = (List<tblLocations>) LocationsRepo.findAll();
        List<LocationToFront> toFront = new ArrayList<>();
        for (tblLocations location : all){
            LocationToFront current = new LocationToFront();
            current.setLon(location.getLon());
            current.setLat(location.getLat());
            current.setLocation_name(location.getLocation_name());
            current.setLocation_id(location.getLocation_id());
            toFront.add(current);
        }
        return new ResponseEntity<>(toFront, HttpStatus.OK);
    }

    @PostMapping(path = "/get/location")
    public @ResponseBody ResponseEntity<?> getLocationForAdmin(
            @RequestParam Integer location_id
    ){
        Optional<tblLocations> location = LocationsRepo.findById(location_id);
        if (location.isPresent()){

            return new ResponseEntity<>(location, HttpStatus.OK);
        }
        return new ResponseEntity<>("Location was not found\n", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/set/custom")
    public @ResponseBody ResponseEntity<?> setCustomTrigger(
            @RequestBody tblCustomTriggers customTrigger
    ){
        Optional<tblCustomTriggers> trigger = CustomTriggersRepo.findById(customTrigger.getCustom_trigger_id());
        if (trigger.isPresent()){
            tblCustomTriggers CustomTrigger = trigger.get();

            CustomTrigger.setCelsius_max(customTrigger.getCelsius_max());
            CustomTrigger.setCelsius_min(customTrigger.getCelsius_min());
            CustomTrigger.setHumidity_max(customTrigger.getHumidity_max());
            CustomTrigger.setHumidity_min(customTrigger.getHumidity_min());
            CustomTrigger.setWind_speed_max(customTrigger.getWind_speed_max());
            CustomTrigger.setWind_speed_min(customTrigger.getWind_speed_min());

            tblCustomTriggers newTrigger = CustomTriggersRepo.save(CustomTrigger);




            Optional<tblLocations> location = LocationsRepo.findById(customTrigger.getCustom_trigger_id());
            if (location.isPresent()){
                tblLocations Location = location.get();
                Location.setCustom_triggerID(newTrigger);
                LocationsRepo.save(Location);

                return new ResponseEntity<>("Custom trigger was saved\n", HttpStatus.OK);
            }

        }


        return new ResponseEntity<>("Nothing has happened...\n", HttpStatus.NOT_FOUND);
    }
}
