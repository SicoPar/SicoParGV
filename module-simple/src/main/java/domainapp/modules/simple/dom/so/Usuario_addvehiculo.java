package domainapp.modules.simple.dom.so;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.types.CarModelo;
import lombok.RequiredArgsConstructor;

@Action(                                                
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "simple")                   
@RequiredArgsConstructor
public class Usuario_addvehiculo {                          

    private final Usuario usuario;                    

    public Usuario act(
            @CarModelo final String name
            
            ) {
        repositoryService.persist(new Vehiculo(usuario, name));
        return usuario;
    }

    @Inject RepositoryService repositoryService;
}