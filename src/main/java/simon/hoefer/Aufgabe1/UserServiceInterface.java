package simon.hoefer.Aufgabe1;

import jakarta.inject.Named;


public interface UserServiceInterface {

    public boolean isUserLoggedIn();

    public UserBean getCurrentUser();

    public boolean isAuthenticateUser(String username, String password);

    public void updateUser(UserBean userBean);
}
