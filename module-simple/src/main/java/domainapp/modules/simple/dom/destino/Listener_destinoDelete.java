package domainapp.modules.simple.dom.destino;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.title.TitleService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Estado;



@Service
public class Listener_destinoDelete {

	@EventListener(Destino_delete.ActionEvent.class)
	public void on(Destino_delete.ActionEvent ev) {
		switch (ev.getEventPhase()) {
		case DISABLE:
			Destino destino = ev.getSubject();

			List<Viaje> viajesRelacionados = viajeRepository.findByDestino(destino);

			
			boolean tienesViajesPendientes = viajesRelacionados.stream()
					.anyMatch(orden -> orden.getEstado() != Estado.Finalizado);

			if (tienesViajesPendientes) {
				ev.disable(String.format("%s tiene viajes pendientes", titleService.titleOf(destino)));
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