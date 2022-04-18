package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@SessionScoped
@Named("profil")
public class ProfilBean implements Serializable {

    @Inject
    private InterfaceInjectionService interfaceInjectionService;

    @Inject
    private FacesContext context;

    private UserServiceInterface userService;

    private UserBean user;


    @PostConstruct
    public void init() {
        this.userService = interfaceInjectionService.getUserInterfaceService();
        this.user = userService.getCurrentUser().Clone();
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void update() {
        if (user.isValid()) {
            this.userService.updateUser(user);
        }
    }

    public String logout() {
        context.getExternalContext().invalidateSession();
        return NavigationStringBuilder.getLogin().withRedirect().build();
    }

    public void abort() {
        this.user = userService.getCurrentUser();
    }
}
