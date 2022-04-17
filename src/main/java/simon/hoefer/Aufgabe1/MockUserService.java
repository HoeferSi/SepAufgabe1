package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class MockUserService implements UserServiceInterface, Serializable {

    @Inject
    private SessionService sessionService;

    private List<UserBean> users = List.of(
            new UserBean("simonH","Simon","Höfer","1","keks"),
            new UserBean("timoD","Timo", "d", "2", "wow")
    );

    @Override
    public boolean isUserLoggedIn() {
        return sessionService.getUsername() != null;
    }

    @Override
    public UserBean getCurrentUser()  {
        String currentUsername = sessionService.getUsername();
        if (currentUsername == null)
        {
            throw new RuntimeException("No user is in session"); // TODO: better exception
        }
        return retrieveUser(currentUsername);
    }

    @Override
    public boolean isAuthenticateUser(String username, String password) {
        UserBean user;
        try {

            user = retrieveUser(username);
        } catch (Exception e)
        {
            // user not found
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public void updateUser(UserBean userBean) {
        this.users = this.users
                .stream()
                .filter(user -> !user.getUsername().equals(userBean.getUsername()))
                .collect(Collectors.toList());
        this.users.add(userBean);
    }

    private UserBean retrieveUser(String username) {
        List<UserBean> matchedUsers =
                users.stream()
                        .filter( userBean -> userBean
                                .getUsername()
                                .equals(username))
                        .collect(Collectors.toList());
        if (matchedUsers.size() != 1) {
            throw new RuntimeException(String.format("internal error found %d users with username",
                    matchedUsers.size()));
        }
        return matchedUsers.get(0);
    }

}
