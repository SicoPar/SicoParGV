package domainapp.modules.simple.fixture;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.Usuarios;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SimpleObject_persona implements PersonaWithBuilderScript<SimpleObjectBuilder>, PersonaWithFinder<Usuario> {

	FOO("Luciano", "Sicolo","42402145","03/02/2000","Luciano.sicolo@hotmail.com","29953332147"), BAR("Diego", "Parra","12/02/1980","42422145","Diego.parra@hotmail.com","2995688796"), BAZ("Fernando", "Gimenez","12/09/1980","12302145","Fernando.gimenez@hotmail.com","299561123"), FRODO("Pedro", "Alfonso","12/12/1980","42402557","Pedro.alfonoso@hotmail.com","2995634112");

	private final String nombre;
	private final String apellido;
	private final String documento;
	private final String fecha_nacimiento;
	private final String email;
	private final String telefono;

	@Override
	public SimpleObjectBuilder builder() {
		return new SimpleObjectBuilder().setApellido(apellido).setNombre(nombre).setDocumento(documento).setEmail(email).setTelefono(telefono);

	}

	@Override
	public Usuario findUsing(final ServiceRegistry serviceRegistry) {
		Usuarios usuarios = serviceRegistry.lookupService(Usuarios.class).orElse(null);
		return usuarios.findByApellidoExact(apellido);
	}

	public static class PersistAll extends PersonaEnumPersistAll<SimpleObject_persona, Usuario> {

		public PersistAll() {
			super(SimpleObject_persona.class);
		}
	}
}
