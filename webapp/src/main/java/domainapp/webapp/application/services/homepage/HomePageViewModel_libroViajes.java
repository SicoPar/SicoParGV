package domainapp.webapp.application.services.homepage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.annotation.Optionality;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.UsuarioRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibleRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible_libroViajes;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Estado;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.types.Patente;
import domainapp.modules.simple.types.prueba;
import lombok.RequiredArgsConstructor;

@Action
@RequiredArgsConstructor
public class HomePageViewModel_libroViajes {

	final HomePageViewModel homePageViewModel;

	public Object act(Usuario usuario, @prueba Usuario pasajero, VehiculosDisponible vehiculosDisponible,
			Destino destino, String razon, LocalDate visitAt, boolean showVisit, Riesgo riesgo, Estado estado) {
		if (usuario != null) {
			Licencia licencia = usuario.getLicencia();

			if (Licencia.No_Contiene_Licencia.equals(licencia)) {
				messageService
						.raiseError("El usuario seleccionado, no puede realizar este viaje porque no tiene licencia");

			} else {

				List<Viaje> viajesExistentes = viajeRepository.findByPatenteAndFecha(vehiculosDisponible.getPatente(),
						visitAt);

				if (!viajesExistentes.isEmpty()) {

					messageService.raiseError("Este vehículo ya tiene un viaje programado para la fecha seleccionada.");
				} else {
					if (Riesgo.alto.equals(riesgo)) {
					    if (pasajero == null) {
					        messageService.raiseError("En caso de Riesgo alto, se requiere un pasajero.");
					    } else if (usuario != null && usuario.equals(pasajero)) {
					        messageService.raiseError("El usuario no puede ser igual al pasajero.");
					    } else {
					        // Realizar la lógica cuando el riesgo es alto, el pasajero no es nulo, y usuario no es igual al pasajero.
					        Viaje viaje = wrapperFactory
					            .wrapMixin(VehiculosDisponible_libroViajes.class, vehiculosDisponible)
					            .act(usuario, pasajero, destino, razon, visitAt, riesgo, estado);
					        return showVisit ? viaje : homePageViewModel;
					    }
					} else {
					    // Realizar la lógica cuando el riesgo no es alto o cuando "pasajero" es nulo.
					    Viaje viaje = wrapperFactory
					        .wrapMixin(VehiculosDisponible_libroViajes.class, vehiculosDisponible)
					        .act(usuario, pasajero, destino, razon, visitAt, riesgo, estado);
					    return showVisit ? viaje : homePageViewModel;
					}
				}
			}
		}
		return homePageViewModel;
	}

	public List<Usuario> autoComplete0Act(final String apellido) {
		return usuarioRepository.findByApellidoContaining(apellido);
	}

	public List<Usuario> autoComplete1Act(final String apellido) {
		return usuarioRepository.findByApellidoContaining(apellido);
	}

	public List<Destino> autoComplete3Act(final String nombre) {
		return destinoRepository.findByNombreContaining(nombre);
	}
//    public List<Destino> autoComplet0Act(final String nombre) {
//        List<Destino> destinos = destinoRepository.findByNameContaining(nombre);
//        return destinos != null ? destinos : Collections.emptyList();
//    }
//    public List<Vehiculo> choices1Act(Usuario usuario) {
//        if(usuario == null) return Collections.emptyList();
//        return vehiculoRepository.findByUsuario(usuario);
//    }

	public List<VehiculosDisponible> autoComplete2Act(final String patente) {
		return vehiculosDisponibleRepository.findByPatenteContaining(patente);
	}

//	public LocalDateTime defaultAct(Usuario usuario, VehiculosDisponible vehiculo) {
//		if (vehiculo == null)
//			return null;
//		return factoryService.mixin(VehiculosDisponible_libroViajes.class, vehiculo).default0Act();
//	}
//
//	public String validate2Act(Usuario usuario, Vehiculo vehiculo, LocalDate viajeAt) {
//		return factoryService.mixin(VehiculosDisponible_libroViajes.class, vehiculo).validate0Act(viajeAt);
//	}

	
	@Inject
	ViajeRepository viajeRepository;
	@Inject
	VehiculosDisponibleRepository vehiculosDisponibleRepository;
	@Inject
	UsuarioRepository usuarioRepository;
	@Inject
	DestinoRepository destinoRepository;
	@Inject
	WrapperFactory wrapperFactory;
	@Inject
	FactoryService factoryService;
	@Inject
	private MessageService messageService;

}