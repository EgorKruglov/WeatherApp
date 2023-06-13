package com.example.demo.controllers;

import com.example.demo.dataToFront.LocationToFront;
import com.example.demo.repos.*;
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
@CrossOrigin
@Controller // This means that this class is a Controller
//@RequestMapping(path="/debug")
public class test {
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
    private final WeatherApiClient weatherApiClient;
    public test(WeatherApiClient weatherApiClient) {
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
            toFront.add(current);
        }
        return new ResponseEntity<>(toFront, HttpStatus.OK);
    }
}
