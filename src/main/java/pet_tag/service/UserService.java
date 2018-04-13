package pet_tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pet_tag.model.User;
import pet_tag.model.UserProfile;
import pet_tag.repo.UserProfileRepo;
import pet_tag.repo.UserRepo;

import java.util.Arrays;
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
        return userRepository.findByEmail(email);
    }

    public ResponseEntity register(String username,
                                   String firstname,
                                   String lastname,
                                   String email,
                                   String password,
                                   UserProfile userProfile){
        if(userRepository.findOneByUsername(username)!=null || userRepository.findByEmail(email)!=null){
            return ResponseEntity.badRequest().body("username or email is taken");
        }
        User newUser = new User(username, firstname, lastname, email, bCryptPasswordEncoder.encode(password), userProfile);
        userRepository.save(newUser);
        if(userRepository.findOneByUsername(username)!=null){
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
        User user = userRepository.findOneByUsername(username);
        return user!=null && bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}