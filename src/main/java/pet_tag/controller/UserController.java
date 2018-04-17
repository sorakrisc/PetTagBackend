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

    @GetMapping("/whoami")
    public ResponseEntity whoami(Authentication auth){
        String username = (String) auth.getPrincipal();
        User user = userrp.findOneByEmail(username);
        Map<String, Object> ret = new HashMap<String, Object>(){{
            put("user",  username);
        }};
        return ResponseEntity.ok(ret);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam("firstname") String firstname,
                                   @RequestParam("lastname") String lastname,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("gender") String gender,
                                   @RequestParam("dateOfBirth")Date dob,
                                   @RequestParam("phone") String phone
                                   ){

        System.out.println(firstname);
//        System.out.println(userProfile.getGender());
//        UserProfile userProfile = null;
//        return ResponseEntity.ok("ok");
       return userService.register(firstname, lastname, email, password,gender,dob, phone);
    }
}
