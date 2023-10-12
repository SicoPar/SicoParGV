package domainapp.modules.simple.dom.so;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.types.Nombre_Control;
import lombok.RequiredArgsConstructor;

@Action(                                                
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "simple")                   
@RequiredArgsConstructor
public class Vehiculo_AgregarService {                          

    private final Vehiculo vehiculo;                    

    public Vehiculo act(
            @Nombre_Control final String name
            
            ) {
        repositoryService.persist(new Service(vehiculo, name ));
        return vehiculo;
    }

    @Inject RepositoryService repositoryService;
}