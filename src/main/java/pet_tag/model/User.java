package pet_tag.model;



import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String username;

    @Email(message = "*Please provide a valid Email")
    @NotNull(message = "*Please provide an email")
    @NotEmpty(message = "*Please provide an email")
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;


    @NotNull
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstname;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name")
    private String lastname;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private UserProfile userProfile;

    public User(String username, String firstname, String lastname, String email, String password, UserProfile userProfile){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.userProfile = userProfile;
    }
    public User(){
    }


    public long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
