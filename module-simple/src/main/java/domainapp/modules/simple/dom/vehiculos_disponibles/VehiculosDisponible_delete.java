package domainapp.modules.simple.dom.vehiculos_disponibles;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.events.domain.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.service.ServiceRepository;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import lombok.RequiredArgsConstructor;

@Action(
        domainEvent = VehiculosDisponible_delete.ActionEvent.class,
        semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(
        associateWith = "simpleObject", position = ActionLayout.Position.PANEL,
        describedAs = "Eliminaras este Vehiculo de la base de datos, y y lo que este relacionado a este ")
@RequiredArgsConstructor
public class VehiculosDisponible_delete {
	 public static class ActionEvent extends ActionDomainEvent<VehiculosDisponible_delete>{}

	    private final VehiculosDisponible vehiculosDisponible;

	    public void act() {

        
	    	List<Viaje> viajesRelacionados = viajeRepository.findByVehiculosDisponibleOrderByFechaDesc(vehiculosDisponible);
	    	 List<Service> servicesRelacionados = serviceRepository.findByVehiculo(vehiculosDisponible);
	    	for (Viaje viaje : viajesRelacionados) {
               
                viaje.setActivo(false);
                repositoryService.persist(viaje);
            }
	    	
	    	for (Service service : servicesRelacionados) {
	               
	    		service.setActivo(false);
                repositoryService.persist(service);
            }
	    	

	    	
	    	vehiculosDisponible.setActivo(false);
	        repositoryService.persist(vehiculosDisponible);
	    }

	    @Inject ViajeRepository viajeRepository;
	    @Inject ServiceRepository serviceRepository;
	    @Inject RepositoryService repositoryService;
	

}






