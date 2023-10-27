package domainapp.modules.simple.dom.so;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.enumeradores.Estado;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.types.Razon;
import domainapp.modules.simple.types.prueba;
import lombok.RequiredArgsConstructor;

@Action(semantics = SemanticsOf.IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
@ActionLayout(associateWith = "vehiculosDisponible", sequence = "1") // Cambio de "simple" a "vehiculo"
@RequiredArgsConstructor
public class VehiculosDisponible_libroViajes {
	public final Usuario usuario;
	public final Usuario pasajero;
	public final VehiculosDisponible vehiculosDisponible;
	public final Destino destino;

	public VehiculosDisponible_libroViajes(VehiculosDisponible vehiculosDisponible) {
		this.usuario = null;
		this.pasajero = null;
		this.vehiculosDisponible = vehiculosDisponible;
		this.destino = null; // Opcional: Puedes inicializar destino aquí si es necesario
	}

	public Viaje act(Usuario usuario,@prueba Usuario pasajero, Destino destino,String razon, LocalDate visitAt,Riesgo riesgo,Estado estado) {
		return repositoryService.persist(new Viaje(usuario,pasajero, vehiculosDisponible, destino,razon,visitAt,riesgo,estado));
	}

	public String validate0Act(LocalDate visitAt) {
		return clockService.getClock().nowAsLocalDate().isBefore(visitAt) ? null : "Must be in the future";
	}

	public List<Usuario> autoComplete0Act(final String apellido) {
		return usuarioRepository.findByApellidoContaining(apellido);
	}

	public List<Destino> autoComplete1Act(final String name) {
		return destinoRepository.findByNameContaining(name);
	}

	public LocalDateTime default0Act() {
		return clockService.getClock().nowAsLocalDateTime().toLocalDate().plusDays(1).atTime(LocalTime.of(9, 0));
	}

	@Inject
	ClockService clockService;
	@Inject
	RepositoryService repositoryService;
	@Inject
	UsuarioRepository usuarioRepository;
	@Inject
	DestinoRepository destinoRepository;
}