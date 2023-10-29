package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.Destinos;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Destino_persona implements PersonaWithBuilderScript<DestinoBuilder>, PersonaWithFinder<Destino> {

	cip("Cipolleti"), nqn("Neuquen");

	private final String nombre;
	

	@Override
	public DestinoBuilder builder() {
		return new DestinoBuilder().setNombre(nombre);

	}
	

	@Override
	public Destino findUsing(final ServiceRegistry serviceRegistry) {
		Destinos destinos = serviceRegistry.lookupService(Destinos.class).orElse(null);
		return destinos.findByNombreExact(nombre);
	}
	


	public static class PersistAll extends PersonaEnumPersistAll<Destino_persona, Destino> {

		public PersistAll() {
			super(Destino_persona.class);
		}
	}
}
