package domainapp.modules.simple.dom.usuario;



import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.services.title.TitleService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Estado;



@Service
public class Listener_usuarioDelete {

	@EventListener(Usuario_delete.ActionEvent.class)
	public void on(Usuario_delete.ActionEvent ev) {
	    switch (ev.getEventPhase()) {
	        case DISABLE:
	            Usuario usuario = ev.getSubject();
	            List<Viaje> viajesUsuario = viajeRepository.findByUsuario(usuario);
	            List<Viaje> viajesPasajero = viajeRepository.findByPasajero(usuario);

	            boolean tieneViajesPendientes = false;

	            if (!viajesUsuario.isEmpty() || !viajesPasajero.isEmpty()) {
	                List<Viaje> viajesRelacionados = new ArrayList<>();
	                viajesRelacionados.addAll(viajesUsuario);
	                viajesRelacionados.addAll(viajesPasajero);

	                tieneViajesPendientes = viajesRelacionados.stream()
	                        .anyMatch(orden -> orden.getEstado() != Estado.Finalizado);
	            }

	            if (tieneViajesPendientes) {
	                ev.disable(String.format("%s tiene viajes pendientes", titleService.titleOf(usuario)));
	                break;
	            }

	            break;
	    }
	}

	@Inject
	TitleService titleService;
	@Inject
	ViajeRepository viajeRepository;
}