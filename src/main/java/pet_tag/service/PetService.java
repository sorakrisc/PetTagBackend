package pet_tag.service;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pet_tag.controller.UserController;
import pet_tag.model.Gender;
import pet_tag.model.Pet;
import pet_tag.model.User;
import pet_tag.repo.PetRepo;
import pet_tag.repo.UserRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("petService")
public class PetService {
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PetRepo petRepository;
    @Autowired UserService userService;
    private String baseURL = "http://localhost:3000"+ "/pet/getPetAnon/";


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
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
        //TODO: generate random qr id.
        String generatedQRCode = "";
        while (generatedQRCode.equals("") && petRepository.findOneByqrId(generatedQRCode) ==null){
            generatedQRCode=randomAlphaNumeric(6);
        }
        ByteArrayOutputStream generatedqrCodePng = generateQR(baseURL+generatedQRCode);
        byte[] savingQRCode = generatedqrCodePng.toByteArray();
        Pet pet = new Pet(name, Gender.valueOf(gender), dob, vaccineStatus, breed, petClass, pic);
        pet.setQrCodePng(savingQRCode);
        user.getPet().add(pet);
        userRepository.save(user);
        return ResponseEntity.ok("pet registered");
    }
    public ByteArrayOutputStream generateQR(String url){
        ByteArrayOutputStream bout =
                QRCode.from(url)
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();
        return bout;
    }
    public ResponseEntity getPet(Authentication auth){
        User user = userService.getUser(auth);
        return ResponseEntity.ok(user.getPet());
    }

    public ResponseEntity getPetAnon(String qrCode){
        Pet pet = petRepository.findOneByqrId(qrCode);
        return ResponseEntity.ok(pet);

    }
}
