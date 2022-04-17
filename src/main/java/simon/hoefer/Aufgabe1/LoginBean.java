package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("login")
@RequestScoped
public class LoginBean {

    public LoginBean() {}

    @PostConstruct
    public void init() {

        if (userService.isUserLoggedIn())
        {
            try {
                context.getExternalContext()
                        .redirect(
                                NavigationStringBuilder
                                        .getLogin()
                                        .build()
                        );
            }
            catch (Exception e)
            {

            }
        }
    }

    @Inject FacesContext context;

    @Inject
    private SessionService sessionService;

    @Inject
    private UserServiceInterface userService;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String submit() {
        if (userService.isAuthenticateUser(username, password))
        {
            sessionService.setUsername(username);
            return NavigationStringBuilder
                    .getProfil()
                    .withRedirect()
                    .build();
        }
        return null;
    }
}
