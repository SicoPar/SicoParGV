package domainapp.modules.simple.dom.vehiculos_disponibles;

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
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.UsuarioRepository;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Estado;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.types.Razon;
import domainapp.modules.simple.types.Pasajero;
import lombok.RequiredArgsConstructor;

@Action(
	    semantics = SemanticsOf.IDEMPOTENT, 
	    commandPublishing = Publishing.ENABLED, 
	    executionPublishing = Publishing.ENABLED
	)
	@ActionLayout(associateWith = "vehiculosDisponible", sequence = "1")
	@RequiredArgsConstructor
	public class VehiculosDisponible_libroViajes {
	    
	    private final VehiculosDisponible vehiculosDisponible;

	    @Inject
	    private MessageService messageService;

	    @Inject
	    private RepositoryService repositoryService;

	    @Inject
	    private ViajeRepository viajeRepository;

	    public Viaje act(Usuario usuario, @Pasajero Usuario pasajero, Destino destino, String razon, LocalDate fecha, Riesgo riesgo) {
	        if (usuario != null) {
	            Licencia licencia = usuario.getLicencia();
	            
	            if (Licencia.Nocontiene.equals(licencia)) {
	                messageService.raiseError("El usuario seleccionado no puede realizar este viaje porque no tiene licencia.");
	                return null;
	            }
	        } else {
	            messageService.raiseError("El usuario no puede ser nulo.");
	            return null;
	        }
	        
	        List<Viaje> viajesExistentes = viajeRepository.findByPatenteAndFecha(vehiculosDisponible.getPatente(), fecha);
	        
	        if (!viajesExistentes.isEmpty()) {
	            messageService.raiseError("Este vehículo ya tiene un viaje programado para la fecha seleccionada.");
	            return null;
	        }
	        
	        if (Riesgo.alto.equals(riesgo)) {
	            if (pasajero == null) {
	                messageService.raiseError("En caso de Riesgo alto, se requiere un pasajero.");
	                return null;
	            }
	        } 
	            if (usuario != null && usuario.equals(pasajero)) {
	                messageService.raiseError("El usuario no puede ser igual al pasajero.");
	                return null;
	            }
	        

	        return repositoryService.persist(new Viaje(usuario, pasajero, vehiculosDisponible, destino, razon, fecha, riesgo));
	    }
	

	public String validate0Act(LocalDate visitAt) {
		return clockService.getClock().nowAsLocalDate().isBefore(visitAt) ? null : "Must be in the future";
	}

	public List<Usuario> autoComplete0Act(final String apellido) {
		return usuarioRepository.findByApellidoContainingAndActivo(apellido,true);
	}

	public List<Usuario> autoComplete1Act(final String apellido) {
		return usuarioRepository.findByApellidoContainingAndActivo(apellido,true);
	}

	public List<Destino> autoComplete2Act(final String nombre) {
		return destinoRepository.findByNombreContainingAndActivo(nombre,true);
	}

	public LocalDateTime default0Act() {
		return clockService.getClock().nowAsLocalDateTime().toLocalDate().plusDays(1).atTime(LocalTime.of(9, 0));
	}

	@Inject
	ClockService clockService;

	@Inject
	UsuarioRepository usuarioRepository;
	@Inject
	DestinoRepository destinoRepository;
}