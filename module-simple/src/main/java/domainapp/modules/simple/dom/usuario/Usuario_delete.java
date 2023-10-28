package domainapp.modules.simple.dom.usuario;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.events.domain.ActionDomainEvent;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import lombok.RequiredArgsConstructor;

@Action(
        domainEvent = Usuario_delete.ActionEvent.class,
        semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(
        associateWith = "Usuario", position = ActionLayout.Position.PANEL,
        describedAs = "Eliminaras este Usuario o Copiloto esta relacionado")
@RequiredArgsConstructor
public class Usuario_delete {
	 public static class ActionEvent extends ActionDomainEvent<Usuario_delete>{}

	    private final Usuario usuario;

	    public void act() {

            
	    	List<Viaje> viajesRelacionados = viajeRepository.findByUsuario(usuario);
	    	for (Viaje viaje : viajesRelacionados) {
             
                viaje.setActivo(false);
                repositoryService.persist(viaje);
            }
	    	
	
	        List<Viaje> viajesPasajero = viajeRepository.findByPasajero(usuario);
	        for (Viaje viaje : viajesPasajero) {
	
	            viaje.setActivo(false);
	            repositoryService.persist(viaje);
	        }


	    	usuario.setActivo(false);
	        repositoryService.persist(usuario);
	    }

	    @Inject ViajeRepository viajeRepository;
	    @Inject RepositoryService repositoryService;
	

}

