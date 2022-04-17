package simon.hoefer.Aufgabe1;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;


public class UserBean implements Serializable {

    private String username;

    private String name;

    private String firstname;

    private String phoneNumber;

    private String password;

    private String confirmedPassword;

    public UserBean() {}

    public UserBean(String username, String name, String firstname, String phoneNumber,
                    String password)
    {
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmedPassword = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public UserBean Clone()
    {
        return new UserBean(this.username, this.name, this.firstname, this.phoneNumber,
                this.password);
    }
}
