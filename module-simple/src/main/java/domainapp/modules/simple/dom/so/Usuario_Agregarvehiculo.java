package domainapp.modules.simple.dom.so;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.types.Modelo;
import domainapp.modules.simple.types.Patente;
import lombok.RequiredArgsConstructor;

@Action(                                                
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "simple")                   
@RequiredArgsConstructor
public class Usuario_Agregarvehiculo {                          

    private final Usuario usuario;                    

    public Usuario act(
            @Patente final String patente,
            @Modelo final String modelo
            
            ) {
        repositoryService.persist(new Vehiculo(usuario, patente,modelo));
        return usuario;
    }
    
    public String validate0Act(final String patente) {
        return repositoryVehiculo.findByUsuarioAndPatente(usuario,patente).isPresent()
                ? String.format("El vehiculo con esta patente ingresada, ya esta definida por este usuario", patente)
                : null;
    }

    @Inject RepositoryService repositoryService;
    @Inject VehiculoRepository repositoryVehiculo;
}
