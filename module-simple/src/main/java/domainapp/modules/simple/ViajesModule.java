package domainapp.modules.simple;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.isis.testing.fixtures.applib.modules.ModuleWithFixtures;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.viaje.Viaje;

@Configuration
@ComponentScan
@EnableJpaRepositories
@EntityScan(basePackageClasses = {ViajesModule.class})
public class ViajesModule implements ModuleWithFixtures {

    @Override
    public FixtureScript getTeardownFixture() {
        return new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                repositoryService.removeAll(Usuario.class);
               repositoryService.removeAll(Destino.class);
               repositoryService.removeAll(VehiculosDisponible.class);
           

            }
        };
    }
}
