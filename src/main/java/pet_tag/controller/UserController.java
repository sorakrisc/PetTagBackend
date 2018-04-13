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
        User user = userrp.findOneByUsername(username);
        Map<String, Object> ret = new HashMap<String, Object>(){{
            put("user",  username);
        }};
        return ResponseEntity.ok(ret);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam("username") String username,
                                   @RequestParam("firstname") String firstname,
                                   @RequestParam("lastname") String lastname,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("userprofile")UserProfile userProfile){


       return userService.register(username, firstname, lastname, email, password, userProfile);
    }
}
