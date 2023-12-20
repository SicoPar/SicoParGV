
package domainapp.modules.simple.dom.usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PriorityPrecedence;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.persistence.jpa.applib.services.JpaSupportService;

import domainapp.modules.simple.types.Nombre;
import domainapp.modules.simple.types.Nombre_Destino;
import domainapp.modules.simple.types.Patente;
import domainapp.modules.simple.types.Telefono;
import net.sf.jasperreports.engine.JRException;
import domainapp.modules.simple.types.Pasajero;
import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.reportes.EjecutarReportes;
import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.service.ServiceRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibleRepository;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.types.Ciudad;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Name;

@DomainService(nature = NatureOfService.VIEW, logicalTypeName = "simple.Usuarios")
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = { @Inject })
public class Usuarios {

	final RepositoryService repositoryService;
	final JpaSupportService jpaSupportService;
	final UsuarioRepository usuarioRepository;

	final ViajeRepository viajeRepository;
	final ServiceRepository serviceRepository;
	final VehiculosDisponibleRepository vehiculosDisponible;
	final DestinoRepository destinoRepository;
	
	Service service;


	@Action(semantics = SemanticsOf.NON_IDEMPOTENT)
	@ActionLayout(promptStyle = PromptStyle.INLINE)
	public Usuario CrearUsuario(@Name final String apellido, @Nombre final String nombre, @Documento final String documento,
			final String fecha_nacimiento,Sector sectores,@Ciudad String ciudad,Genero genero,Licencia licencia, @Email final String email, @Telefono final String telefono

	) {
		return repositoryService.persist(Usuario.withName(apellido, nombre,documento,fecha_nacimiento,sectores,ciudad,genero,licencia,email, telefono));
	}

	

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
	public List<Usuario> BuscarUsuarioActivoPorApellido(@Name final String apellido) {
		return usuarioRepository.findByApellidoContainingAndActivo(apellido,true);
	}
	
	
	
	

	@Programmatic
	public Usuario findByApellidoExact(final String apellido) {
		return usuarioRepository.findByApellido(apellido);
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Usuario> ListaDeUsuariosActivos() {
		 return usuarioRepository.findByActivo(true);
	}
	


//	@Action(semantics=SemanticsOf.SAFE)
//	@ActionLayout(bookmarking=BookmarkPolicy.AS_ROOT)
//	public Blob ExportarListadoUsuarios() throws JRException, IOException{
//		EjecutarReportes ejecutarReportes = new EjecutarReportes();
//		return ejecutarReportes.ListadoUsuarios(ListaDeUsuariosActivos());
//	}	
	
	
	
		
	@Programmatic
	public void ping() {
		jpaSupportService.getEntityManager(Usuario.class).ifSuccess(entityManager -> {
			final TypedQuery<Usuario> q = entityManager
					.createQuery("SELECT p FROM SimpleObject p ORDER BY p.name", Usuario.class).setMaxResults(1);
			q.getResultList();
		});
	}
	@Inject VehiculosDisponibleRepository vehiculosDisponiblerepository;
}
