package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import org.jboss.weld.context.http.Http;

import java.io.Serializable;

@SessionScoped
public class SessionService implements Serializable {

    private final String SESSION_USERNAME = "username";

    @Inject
    private FacesContext context;

    private HttpSession session;

    public SessionService() {}

    public SessionService(FacesContext context) {
        this.context = context;
        init();
    }

    @PostConstruct
    private void init() {
        session = (HttpSession) context.getExternalContext().getSession(true);
    }

    public void setUsername(String username)
    {
        session.setAttribute(SESSION_USERNAME,username);
    }

    public String getUsername() {
        /*if (session == null) {
            return null;
        }*/
        return (String) session.getAttribute(SESSION_USERNAME);
    }
}
