package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Usuario_persona implements PersonaWithBuilderScript<UsuarioBuilder>, PersonaWithFinder<Usuario> {

	FOO("Luciano", "Sicolo","42402145",LocalDate.of(2000, 2, 3),Sector.Dirección,"Cipolletti",Genero.Masculino,Licencia.Contiene_Licencia,"Luciano.sicolo@hotmail.com","29953332147"), BAR("Diego", "Parra","42422145",LocalDate.of(2000, 2, 3),Sector.Marketing,"Neuquen",Genero.Masculino,Licencia.No_Contiene_Licencia,"Diego.parra@hotmail.com","2995688796");

	private final String nombre;
	private final String apellido;
	private final String documento;
	private final LocalDate fecha_nacimiento;
	private final Sector sector;
	private final String ciudad;
	private final Genero genero;
	private final Licencia licencia;
	private final String email;
	private final String telefono;

	@Override
	public UsuarioBuilder builder() {
		return new UsuarioBuilder().setApellido(apellido).setNombre(nombre).setDocumento(documento).setFecha_nacimiento(fecha_nacimiento).setSector(sector).setCiudad(ciudad).setGenero(genero).setLicencia(licencia).setEmail(email).setTelefono(telefono);

	}
	

	
	
	
	

	@Override
	public Usuario findUsing(final ServiceRegistry serviceRegistry) {
		Usuarios usuarios = serviceRegistry.lookupService(Usuarios.class).orElse(null);
		return usuarios.findByApellidoExact(apellido);
	}

	public static class PersistAll extends PersonaEnumPersistAll<Usuario_persona, Usuario> {

		public PersistAll() {
			super(Usuario_persona.class);
		}
	}
}
