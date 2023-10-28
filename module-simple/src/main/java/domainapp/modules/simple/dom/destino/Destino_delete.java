package domainapp.modules.simple.dom.destino;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.events.domain.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.service.ServiceRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import lombok.RequiredArgsConstructor;

@Action(
        domainEvent = Destino_delete.ActionEvent.class,
        semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(
        associateWith = "simpleObject", position = ActionLayout.Position.PANEL,
        describedAs = "Eliminaras este Destino de la base de datos, y y lo que este relacionado a este ")
@RequiredArgsConstructor
public class Destino_delete {
	 public static class ActionEvent extends ActionDomainEvent<Destino_delete>{}

	    private final Destino destino;

	    public void act() {

        
	    	List<Viaje> viajesRelacionados = viajeRepository.findByDestino(destino);
	    	for (Viaje viaje : viajesRelacionados) {
               
                viaje.setActivo(false);
                repositoryService.persist(viaje);
            }
	    
	    	

	    	
	    	destino.setActivo(false);
	        repositoryService.persist(destino);
	    }

	    @Inject ViajeRepository viajeRepository;
	    @Inject ServiceRepository serviceRepository;
	    @Inject RepositoryService repositoryService;
	

}






