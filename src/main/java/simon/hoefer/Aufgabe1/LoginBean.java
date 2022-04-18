package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.ValidationException;

@Named("login")
@RequestScoped
public class LoginBean extends AbstractKomplexValidationBean{

    @Inject
    private FacesContext context;

    @Inject
    private SessionService sessionService;

    @Inject
    private InterfaceInjectionService interfacesInjection;

    private UserServiceInterface userService;

    public LoginBean() {}

    @PostConstruct
    public void init() {
        this.userService = interfacesInjection.getUserInterfaceService();
    }


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
        if (isValid()) {
            sessionService.setUsername(username);
            return NavigationStringBuilder
                    .getProfil()
                    .withRedirect()
                    .build();
        }
        return null;
    }

    @Override
    public boolean isValid() {
        if (!userService.isAuthenticateUser(username, password)) {
            setErrorMsg("Wrong password / username");
            return false;
        }
        resetErrorMsg();
        return true;
    }
}
