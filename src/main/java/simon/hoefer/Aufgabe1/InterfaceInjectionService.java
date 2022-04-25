package simon.hoefer.Aufgabe1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import simon.hoefer.Aufgabe1.persistence.DBUserService;

@ApplicationScoped
public class InterfaceInjectionService {

    UserServiceInterface getUserInterfaceService() {
        return CDI.current().select(DBUserService.class).get();
    }
}
