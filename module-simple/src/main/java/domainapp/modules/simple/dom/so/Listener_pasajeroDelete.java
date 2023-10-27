package domainapp.modules.simple.dom.so;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.title.TitleService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import domainapp.modules.simple.enumeradores.Estado;



@Service
public class Listener_pasajeroDelete {

	@EventListener(Pasajero_delete.ActionEvent.class)
	public void on(Pasajero_delete.ActionEvent ev) {
		switch (ev.getEventPhase()) {
		case DISABLE:
			Usuario pasajero = ev.getSubject();

			List<Viaje> viajesRelacionados = viajeRepository.findByUsuario(pasajero);

			// Verifica si hay alguna orden con estado diferente de "Finalizado"
			boolean tieneViajesPendientes = viajesRelacionados.stream()
					.anyMatch(orden -> orden.getEstado() != Estado.Finalizado);

			if (tieneViajesPendientes) {
				ev.disable(String.format("%s tiene viajes pendientes", titleService.titleOf(pasajero)));
				break; // No es necesario continuar verificando las demás órdenes
			}

			break;
		}
	}

	@Inject
	TitleService titleService;
	@Inject
	ViajeRepository viajeRepository;
}