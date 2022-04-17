package simon.hoefer.Aufgabe1;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

import java.io.Serializable;

@SessionScoped
public class Navigator implements Serializable {

    @Inject
    private FacesContext context;

    private NavigationHandler navigator;

    public Navigator() {}

    /**
     * Constructor to make a Navigator the old way
     * @param context
     */
    public Navigator(FacesContext context) {
        this.context = context;
        init();
    }

    @PostConstruct
    public void init() {
        navigator = context.getApplication().getNavigationHandler();
    }

    public void navigateTo(NavigationStringBuilder navigationStringBuilder) {
        navigator.handleNavigation(context, null, navigationStringBuilder.build());
    }

    public void navigateTo(NavigationStringBuilder navigationStringBuilder,
                           boolean completeRequest) {
        navigateTo(navigationStringBuilder);
        if (completeRequest) {
            context.responseComplete();
        }
    }

}
