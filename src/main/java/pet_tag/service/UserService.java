package pet_tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pet_tag.model.Gender;
import pet_tag.model.User;
import pet_tag.model.UserProfile;
import pet_tag.repo.UserProfileRepo;
import pet_tag.repo.UserRepo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Service("userService")
public class UserService{
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserProfileRepo userProfileRepo;
    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
    public ResponseEntity isValidEmail(String email){
        if( findUserByEmail(email) == null){
            return ResponseEntity.ok("valid");
        }
        else{
            return ResponseEntity.ok("invalid");
        }
    }
    public ResponseEntity register(String firstname,
                                   String lastname,
                                   String email,
                                   String password,
                                   String gender,
                                   Date dob,
                                   String phone,
                                   String address,
                                   String city,
                                   String state,
                                   String country,
                                   String zipcode){
        if(userRepository.findOneByEmail(email)!=null){
            return ResponseEntity.badRequest().body("username or email is taken");
        }
        UserProfile userProfile= new UserProfile(phone, Gender.valueOf(gender),dob, address, city, state, country, zipcode);
        User newUser = new User(firstname, lastname, email, bCryptPasswordEncoder.encode(password), userProfile);
        saveUser(newUser);
        if(userRepository.findOneByEmail(email)!=null){
            return ResponseEntity.ok("registered");
        } else{
            return ResponseEntity.ok("not registered");
        }
    }
    public User getUser(Authentication auth){
        String username = (String) auth.getPrincipal();
        User user = userRepository.findOneByEmail(username);
        return user;
    }
    public ResponseEntity update(Authentication auth,
                                 String firstname,
                                 String lastname,
                                 String gender,
                                 Date dob,
                                 String phone,
                                 String address,
                                 String city,
                                 String state,
                                 String country,
                                 String zipcode){
        User user = getUser(auth);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        UserProfile userProfile = user.getUserProfile();
        userProfile.setGender(Gender.valueOf(gender));
        userProfile.setDateOfBirth(dob);
        userProfile.setPhoneNumber(phone);
        userProfile.setAddress(address);
        userProfile.setCity(city);
        userProfile.setState(state);
        userProfile.setCountry(country);
        userProfile.setZipCode(zipcode);
        saveUser(user);
        return ResponseEntity.ok("updated");
    }
    public ResponseEntity updatePassword(Authentication auth, String password){
        User user = getUser(auth);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        saveUser(user);
        return ResponseEntity.ok("password updated");
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public boolean authenticate(String email, String password){
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()){
            return false;
        }
        User user = userRepository.findOneByEmail(email);
        return user!=null && bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}