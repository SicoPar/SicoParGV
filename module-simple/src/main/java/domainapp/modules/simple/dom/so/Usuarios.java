package domainapp.modules.simple.dom.so;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PriorityPrecedence;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jpa.applib.services.JpaSupportService;

import domainapp.modules.simple.types.Nombre;
import domainapp.modules.simple.types.Patente;
import domainapp.modules.simple.types.Telefono;
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
	final VehiculoRepository vehiculoRepository;
	final ViajeRepository viajeRepository;

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT)
	@ActionLayout(promptStyle = PromptStyle.INLINE)
	public Usuario CrearUsuario(@Name final String apellido, @Nombre final String nombre, @Documento final String documento,
			final LocalDate fecha_nacimiento, @Email final String email, @Telefono final String telefono

	) {
		return repositoryService.persist(Usuario.withName(apellido, nombre,documento,fecha_nacimiento, email, telefono));
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
	public List<Vehiculo> findByPatente(@Patente final String patente) {
		return repositoryService.allMatches(Query.named(Vehiculo.class, Vehiculo.NAMED_QUERY__FIND_BY_PATENTE)
				.withParameter("patente", "%" + patente + "%"));
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
	public List<Usuario> BuscarUsuarioPorApellido(@Name final String apellido) {
		return usuarioRepository.findByApellidoContaining(apellido);
	}

	@Programmatic
	public Usuario findByApellidoExact(final String apellido) {
		return usuarioRepository.findByApellido(apellido);
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Usuario> ListaDeUsuarios() {
		return usuarioRepository.findAll();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Vehiculo> ListaDeVehiculos() {
		return vehiculoRepository.findAll();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Viaje> ListaDeViajes() {
		return viajeRepository.findAll();
	}

	@Programmatic
	public void ping() {
		jpaSupportService.getEntityManager(Usuario.class).ifSuccess(entityManager -> {
			final TypedQuery<Usuario> q = entityManager
					.createQuery("SELECT p FROM SimpleObject p ORDER BY p.name", Usuario.class).setMaxResults(1);
			q.getResultList();
		});
	}

}
