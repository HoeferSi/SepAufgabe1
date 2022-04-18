package simon.hoefer.Aufgabe1;

import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.util.List;

public class DBUserService implements UserServiceInterface{

    @Inject SQLService sqlService;

    @Inject
    private SessionService sessionService;

    @Override
    public boolean isUserLoggedIn() {
        return sessionService.getUsername() != null;
    }

    @Override
    public UserBean getCurrentUser() {
        String currentUsername = sessionService.getUsername();
        if (currentUsername == null)
        {
            throw new RuntimeException("No user is in session"); // TODO: better exceptions
        }
        return sqlService
                .getUsers()
                .stream()
                .filter(u -> u.getUsername().equals(currentUsername))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean isAuthenticateUser(final String username, String password) {
        return sqlService
                .getUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .map(u -> u.getPassword().equals(password))
                .findFirst()
                .orElse(false);
    }

    @Override
    public void updateUser(UserBean userBean) {
        sqlService.updateUser(userBean);
    }
}
