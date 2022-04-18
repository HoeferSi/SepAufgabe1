package simon.hoefer.Aufgabe1;

import java.io.Serializable;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;


public class UserBean extends AbstractKomplexValidationBean implements Serializable {

    private String username;

    private String name;

    private String firstname;

    private String address;

    private String password;

    private String confirmedPassword;

    private Date birthdate;

    public UserBean() {}

    public UserBean(String username, String name, String firstname, String address,
                    String password, Date birthdate)
    {
        this.username = username;
        this.name = name;
        this.firstname = firstname;
        this.address = address;
        this.password = password;
        this.confirmedPassword = password;
        this.birthdate = birthdate;
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

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public Date getBirthdate() {
        return birthdate;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public void setBirthdate(Date date) {
        this.birthdate = date;
    }

    @Override
    public boolean isValid() {
        if (!this.password.equals(confirmedPassword)) {
            super.setErrorMsg("Die beiden Passwörter sind leider nicht gleich.");
            return false;
        }
        resetErrorMsg();
        return true;
    }

    public UserBean Clone()
    {
        return new UserBean(this.username, this.name, this.firstname, this.address,
                this.password, this.birthdate);
    }


}
