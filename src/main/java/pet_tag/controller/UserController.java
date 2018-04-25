package pet_tag.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pet_tag.model.User;
import pet_tag.model.UserProfile;
import pet_tag.repo.UserRepo;
import pet_tag.service.UserService;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userrp;

    @Autowired
    private UserService userService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUser(Authentication auth){
        String username = (String) auth.getPrincipal();
        User user = userrp.findOneByEmail(username);
        return user;
    }
    @GetMapping("/whoami")
    public ResponseEntity whoami(Authentication auth){
        User user = getUser(auth);
        Map<String, Object> ret = new HashMap<String, Object>(){{
            put("user",  user.getEmail());
        }};
        return ResponseEntity.ok(ret);
    }
    public int ageCalculator(Date bDay){
        LocalDate newbDay = LocalDate.of(bDay.getYear(), bDay.getMonth(), bDay.getDay());
        System.out.println(newbDay.getYear());
        System.out.println(LocalDate.now());
        System.out.println(Period.between(newbDay, LocalDate.now()).getYears());
        return Period.between(newbDay,LocalDate.now()).getYears();
    }
    public Map<String, Object> getUserInfoHelper(Authentication auth){
        String username = (String) auth.getPrincipal();
        User user = userrp.findOneByEmail(username);
        UserProfile profile = user.getUserProfile();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        Map<String, Object> ret = new HashMap<String, Object>(){{
            put("email",  user.getEmail());
            put("firstname", user.getFirstname());
            put("lastname", user.getLastname());
            put("day", dfDay.format(profile.getDateOfBirth()));
            put("month", dfMonth.format(profile.getDateOfBirth()));
            put("year", df.format(profile.getDateOfBirth()));
            put("country", profile.getCountry());
            put("address", profile.getAddress());
            put("gender", profile.getGender());
            put("state", profile.getState());
            put("zipcode", profile.getState());
            put("city", profile.getCity());
            put("phone", profile.getPhoneNumber());

        }};
        return ret;
    }
    @GetMapping("/getInfo")
    public  ResponseEntity getInfo(Authentication auth){
        Map<String, Object> ret = getUserInfoHelper(auth);
        return ResponseEntity.ok(ret);
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestParam("firstname") String firstname,
                                   @RequestParam("lastname") String lastname,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("gender") String gender,
                                   @RequestParam("dateOfBirth")Date dob,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("address") String address,
                                   @RequestParam("city") String city,
                                   @RequestParam("state") String state,
                                   @RequestParam("country") String country,
                                   @RequestParam("zipcode") String zipcode
                                   ){

        System.out.println(firstname);
//        System.out.println(userProfile.getGender());
//        UserProfile userProfile = null;
//        return ResponseEntity.ok("ok");
       return userService.register(firstname, lastname, email, password,gender,dob, phone, address, city, state, country, zipcode);
    }
}
