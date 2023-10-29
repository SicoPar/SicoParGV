package domainapp.webapp.application.fixture.scenarios;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.isis.testing.fixtures.applib.modules.ModuleWithFixturesService;

import domainapp.modules.simple.fixture.Destino_persona;
import domainapp.modules.simple.fixture.Usuario_persona;
import domainapp.modules.simple.fixture.VehiculosDisponible_persona;

public class DomainAppDemo extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(this, moduleWithFixturesService.getTeardownFixture());
        ec.executeChild(this, new Usuario_persona.PersistAll());
        ec.executeChild(this, new Destino_persona.PersistAll());
        ec.executeChild(this, new VehiculosDisponible_persona.PersistAll());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
