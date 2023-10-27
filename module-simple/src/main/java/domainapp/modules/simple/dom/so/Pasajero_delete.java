package domainapp.modules.simple.dom.so;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.events.domain.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import lombok.RequiredArgsConstructor;

@Action(
        domainEvent = Pasajero_delete.ActionEvent.class,
        semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(
        associateWith = "Usuario", position = ActionLayout.Position.PANEL,
        describedAs = "Eliminaras este Vidrio de la base de datos, y las ordenes de trabajo que sean de este Vidrio.")
@RequiredArgsConstructor
public class Pasajero_delete {
	 public static class ActionEvent extends ActionDomainEvent<Pasajero_delete>{}

	    private final Usuario pasajero;

	    public void act() {

            // Buscar órdenes de trabajo dentro del vidrio
	    	List<Viaje> viajesRelacionados = viajeRepository.findByPasajero(pasajero);
	    	for (Viaje viaje : viajesRelacionados) {
                // Marcar la orden de trabajo como inactiva
                viaje.setActivo(false);
                repositoryService.persist(viaje);
            }

	    	 // Cambia el estado "activo" del vidrio a false en lugar de eliminarlo
	    	pasajero.setActivo(false);
	        repositoryService.persist(pasajero);
	    }

	    @Inject ViajeRepository viajeRepository;
	    @Inject RepositoryService repositoryService;
	

}

