package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.title.TitleService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Estado;



@Service
public class Listener_vehiculosDisponibleDelete {

	@EventListener(VehiculosDisponible_delete.ActionEvent.class)
	public void on(VehiculosDisponible_delete.ActionEvent ev) {
		switch (ev.getEventPhase()) {
		case DISABLE:
			VehiculosDisponible vehiculosDisponible = ev.getSubject();

			List<Viaje> viajesVehiculo = viajeRepository.findByVehiculosDisponibleOrderByFechaDesc(vehiculosDisponible);

			
			boolean tienesViajesPendientes = viajesVehiculo.stream()
					.anyMatch(orden -> orden.getEstado() != Estado.Finalizado);

			if (tienesViajesPendientes) {
				ev.disable(String.format("%s tiene viajes pendientes", titleService.titleOf(vehiculosDisponible)));
				break; //
			}

			break;
		}
	}

	@Inject
	TitleService titleService;
	@Inject
	ViajeRepository viajeRepository;
}