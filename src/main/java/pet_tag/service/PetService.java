package pet_tag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import pet_tag.controller.UserController;
import pet_tag.model.Gender;
import pet_tag.model.Pet;
import pet_tag.model.User;
import pet_tag.repo.UserRepo;

import java.util.Date;

public class PetService {
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepo userRepository;

    public ResponseEntity addPet(Authentication auth,
                                 String gender,
                                 Date dob,
                                 String name,
                                 String vaccineStatus,
                                 String breed,
                                 String petClass,
                                 byte[] pic

    ){
        User user = userController.getUser(auth);
        Pet pet = new Pet(name, Gender.valueOf(gender), dob, vaccineStatus, breed, petClass, pic);
        user.getPet().add(pet);
        userRepository.save(user);
        return ResponseEntity.ok("pet registered");
    }
}
