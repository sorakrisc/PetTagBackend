package pet_tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity register(String firstname,
                                   String lastname,
                                   String email,
                                   String password,
                                   String gender,
                                   Date dob,
                                   String phone){
        if(userRepository.findOneByEmail(email)!=null){
            return ResponseEntity.badRequest().body("username or email is taken");
        }
        UserProfile userProfile= new UserProfile();
        userProfile.setGender(Gender.valueOf(gender));
        userProfile.setPhoneNumber(phone);
        userProfile.setDateOfBirth(dob);

        User newUser = new User(firstname, lastname, email, bCryptPasswordEncoder.encode(password), userProfile);
        userRepository.save(newUser);
        if(userRepository.findOneByEmail(email)!=null){
            return ResponseEntity.ok("registered");
        } else{
            return ResponseEntity.badRequest().body("not registered");
        }
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public boolean authenticate(String username, String password){
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()){
            return false;
        }
        User user = userRepository.findOneByEmail(username);
        return user!=null && bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}