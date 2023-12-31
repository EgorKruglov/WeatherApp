package com.example.demo.controllers;


import com.example.demo.dataToFront.LocationToFront;
import com.example.demo.dataToFront.UserLocationRelation;
import com.example.demo.dataToFront.UserProfile;
import com.example.demo.repos.*;
import com.example.demo.tables.*;
import com.example.demo.weatherHandle.WeatherApiClient;
import com.example.demo.dataToFront.WeatherToFront;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="/WeatherAppDB") // This means URL's start with /demo (after Application path)
public class MainController {

// This means to get the bean called userRepository
// Which is auto-generated by Spring, we will use it to handle the data
    //Подключаю таблицы
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
    public MainController(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }


    @PostMapping(path = "/auth")
    public ResponseEntity <?> authUser(
            @RequestParam String  login,
            @RequestParam String password){
        List<tblUsers> allUsers = (List<tblUsers>) UsersRepo.findAll();
        for (tblUsers user : allUsers){

            if ((user.getPhone_number().toString().equals(login) || Objects.equals(user.getEmail(), login)) && Objects.equals(user.getPassword(), password)){
                System.out.println("Authorised");
                return new ResponseEntity<>(user.getUser_id(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User was not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/getForecast")
    public @ResponseBody ResponseEntity <?> getLocationWeatherSeven(
            // @RequestParam Integer user_id,
            @RequestParam Integer location_id
    ) {
        Optional<tblLocations> location =  LocationsRepo.findById(location_id);
        WeatherToFront weatherToFront = new WeatherToFront();

        location.ifPresent(Location ->{
            weatherToFront.setLocation(Location.getLocation_name());
            weatherToFront.setLat(Location.getLat());
            weatherToFront.setLon(Location.getLon());
            try {
                weatherToFront.setWeather(weatherApiClient.getWeatherSeven(Location.getLat(), Location.getLon()));
            } catch (JsonProcessingException | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        if (location.isPresent()){
            return new ResponseEntity<>(weatherToFront, HttpStatus.OK);
        }

        return new ResponseEntity<>("Location was not found\n", HttpStatus.NOT_FOUND);
    }


    @PostMapping(path="/add/user") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity <?>  addNewUser ( // @ResponseBody means the returned String is the response, not a view name
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Long phone_number,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Byte root) {
        // @RequestParam means it is a parameter from the GET or POST request

        tblUsers user = new tblUsers();
        user.setName(name);
        user.setSurname(surname);
        user.setPhone_number(phone_number);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoot(root);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setRegistr_date(java.sql.Date.valueOf(dateFormat.format(date)));
        user = UsersRepo.save(user);
        System.out.println("USER ID:" +user.getUser_id());

        return new ResponseEntity<>(user.getUser_id(), HttpStatus.OK);
    }
@PostMapping(path = "/add/AdminCode")
public @ResponseBody ResponseEntity <?> addAdminCode(
        @RequestParam Integer user_id,
        @RequestParam String code){

        tblAdminCodes admin = new tblAdminCodes();
        Optional<tblUsers> user = UsersRepo.findById(user_id);
    if (user.isPresent()){ tblUsers User = user.get();
        if (User.getRoot() == 1){
            admin.setCode(code);
            admin.setAdminUsers(User);
            AdminCodesRepo.save(admin);
            return new ResponseEntity<>("Code was saved\n", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User is not admin\n", HttpStatus.CONFLICT);
        }
    }
    return new ResponseEntity<>("Admin was not found\n", HttpStatus.NOT_FOUND);
}

@PostMapping(path = "/check/code")
public @ResponseBody ResponseEntity<?> checkAdminCode(
        @RequestParam String code
){
        Optional<tblAdminCodes> admin = AdminCodesRepo.findByCode(code);
        if (admin.isPresent()){
            return new ResponseEntity<>("Accepted", HttpStatus.OK);
        }
    return new ResponseEntity<>("Code was wrong or smth\n", HttpStatus.NOT_FOUND);
}


    @PostMapping(path = "/getProfile")
    public @ResponseBody ResponseEntity<?> getUserProfile(
            @RequestParam Integer user_id
    ){
        Optional<tblUsers> user = UsersRepo.findById(user_id);
        UserProfile userProfile = new UserProfile();

        if (user.isPresent()){
            tblUsers User = user.get();
            userProfile.setEmail(User.getEmail());
            userProfile.setUser_idId(User.getUser_id());
            userProfile.setName(User.getName());
            userProfile.setSurname(User.getSurname());
            userProfile.setPhone_number(User.getPhone_number());
            userProfile.setRoot(User.getRoot());

            List<LocationToFront> locations = new ArrayList<>();
            Iterable<tblLocationUserRelations> allRelations = LocationUserRelationsRepo.findAll();
            for (tblLocationUserRelations relation : allRelations){
                if (Objects.equals(relation.getUserId(), User)){
                    LocationToFront location = new LocationToFront();
                    location.setLat(relation.getLocations().getLat());
                    location.setLocation_name(relation.getLocations().getLocation_name());
                    location.setLon(relation.getLocations().getLon());
                    location.setLocation_id(relation.getLocations().getLocation_id());
                    locations.add(location);
                }
            }
            userProfile.setLocations(locations);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        }
        return new ResponseEntity<>("User was not found\n", HttpStatus.NOT_FOUND);
    }

    //Чтение всех полей в таблицах

    @GetMapping(path="/all/tblUsers")
    public @ResponseBody Iterable<tblUsers> getAllUsers() {
        // This returns a JSON or XML with the users
        return UsersRepo.findAll();
    }

/*    @GetMapping(path="/all/tblLocationUserRelations")
    public @ResponseBody Iterable<tblLocationUserRelations> getAllLocationUserRelations() {
        return LocationUserRelationsRepo.findAll();
    }*/

    @GetMapping(path="/all/tblLocationUserRelations")
    public @ResponseBody ResponseEntity<?> relationsToFront() {
        Iterable<tblLocationUserRelations> allRelations = LocationUserRelationsRepo.findAll();

        UserLocationRelation simpleRelations = new UserLocationRelation();

        for (tblLocationUserRelations relation : allRelations) {
            Integer targetUser = relation.getUserId().getUser_id();
            String targetLocation = relation.getLocations().getLocation_name();
            simpleRelations.addRelation(targetUser, targetLocation);
        }
        return new ResponseEntity<>(simpleRelations, HttpStatus.OK);
    }




}