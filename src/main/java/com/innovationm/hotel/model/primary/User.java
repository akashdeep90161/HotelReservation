package com.innovationm.hotel.model.primary;

import com.innovationm.hotel.model.secondary.Location;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "firstName can not be null")
    private String firstName;
    @NotNull(message = "lastName can not be null")
    private String lastName;

    @NotNull
    @Size(min=10, max = 10, message = "Phone is 10 digits only")
    private String phone;
    @NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.validate}")
   // @UniqueElements(message = "{email.uniqueelement}")
    private String email;
    @NotNull(message = "password can not be null")
    private String pass;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
