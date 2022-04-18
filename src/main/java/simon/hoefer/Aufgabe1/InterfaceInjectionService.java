package simon.hoefer.Aufgabe1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;

@ApplicationScoped
public class InterfaceInjectionService {

    UserServiceInterface getUserInterfaceService() {
        return CDI.current().select(DBUserService.class).get();
    }
}
