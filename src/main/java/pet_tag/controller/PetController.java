package pet_tag.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pet_tag.model.Gender;
import pet_tag.model.Pet;
import pet_tag.model.User;
import pet_tag.repo.PetRepo;
import pet_tag.service.PetService;
import pet_tag.service.UserService;

import java.util.Date;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetRepo petRepo;

    @Autowired
    private PetService petService;

    @GetMapping("/getPet")
    public ResponseEntity getPet(Authentication auth){
        return petService.getPet(auth);
    }
    //TODO: get petAnon
    @PostMapping("/addPetAnon")
    public ResponseEntity getPetAnon(String qrCode){
        return petService.getPetAnon(qrCode);
    }

    public ResponseEntity addPet(Authentication auth,
                                 @RequestParam("gender") String gender,
                                 @RequestParam("dob")Date dob,
                                 @RequestParam("name") String name,
                                 @RequestParam("vaccineStatus") String vaccineStatus,
                                 @RequestParam("breed") String breed,
                                 @RequestParam("petClass") String petClass,
                                 @RequestParam("pic") byte[] pic

    ){
        return petService.addPet(auth,gender, dob, name, vaccineStatus, breed, petClass, pic);
    }
}
