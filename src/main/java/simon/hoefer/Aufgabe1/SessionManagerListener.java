package simon.hoefer.Aufgabe1;

import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Inject;

public class SessionManagerListener implements PhaseListener {

    @Inject
    private Navigator navigator;

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        SessionService sessionService = new SessionService(context);
        Navigator navigator = new Navigator(context);
        String url = context.getViewRoot().getViewId();
        if (url.endsWith(NavigationStringBuilder.getProfil().build())
                && sessionService.getUsername() == null) {
            navigator.navigateTo(
                    NavigationStringBuilder
                            .getLogin()
                            .withRedirect(),
                    true);
        }
        if (url.endsWith(NavigationStringBuilder.getLogin().build())
                && sessionService.getUsername() != null) {
            navigator.navigateTo(
                    NavigationStringBuilder
                            .getProfil()
                            .withRedirect(),
                    false);
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
