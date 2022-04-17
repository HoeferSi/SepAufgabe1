package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@SessionScoped
@Named("profil")
public class ProfilBean implements Serializable {

    @Inject
    private UserServiceInterface userService;

    private UserBean user;

    @Inject
    private FacesContext context;

    @PostConstruct
    public void init() {
        this.user = userService.getCurrentUser().Clone();
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void update() {
        this.userService.updateUser(user);
    }

    public String logout() {
        context.getExternalContext().invalidateSession();
        return NavigationStringBuilder.getLogin().withRedirect().build();
    }

    public void abort() {
        this.user = userService.getCurrentUser();
    }
}
