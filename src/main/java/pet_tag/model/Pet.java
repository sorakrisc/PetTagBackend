package pet_tag.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "petTB")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long petId;

    @NotNull
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "dob")
    private Date dob;

    private String vaccineStatus;

    private String breed;

    private String petClass;

    @Column(unique = true)
    private String qrId;

    @Lob
    @Column(name="pic")
    private byte[] pic;

    @Lob
    private byte[] qrCodePng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
//    @JsonBackReference
    private User user;

    public Pet(){

    }
    public Pet(String name, Gender gender, Date dob, String vaccineStatus, String breed, String petClass, byte[] pic){
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.vaccineStatus = vaccineStatus;
        this.breed = breed;
        this.petClass = petClass;
        this.pic =pic;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getVaccineStatus() {
        return vaccineStatus;
    }

    public void setVaccineStatus(String vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }


    public String getPetClass() {
        return petClass;
    }

    public void setPetClass(String petClass) {
        this.petClass = petClass;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    public byte[] getQrCodePng() {
        return qrCodePng;
    }

    public void setQrCodePng(byte[] qrCodePng) {
        this.qrCodePng = qrCodePng;
    }
}
