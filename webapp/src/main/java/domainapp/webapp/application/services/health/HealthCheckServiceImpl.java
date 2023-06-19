package domainapp.webapp.application.services.health;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import org.apache.isis.applib.services.health.Health;
import org.apache.isis.applib.services.health.HealthCheckService;

import domainapp.modules.simple.dom.so.Usuarios;

@Service
@Named("domainapp.HealthCheckServiceImpl")
public class HealthCheckServiceImpl implements HealthCheckService {

    private final Usuarios usuarios;

    @Inject
    public HealthCheckServiceImpl(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public Health check() {
        try {
            usuarios.ping();
            return Health.ok();
        } catch (Exception ex) {
            return Health.error(ex);
        }
    }
}
