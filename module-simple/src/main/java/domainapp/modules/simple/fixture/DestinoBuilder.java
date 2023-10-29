package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.Destinos;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DestinoBuilder extends BuilderScriptWithResult<Destino> {

    @Getter @Setter
    private String nombre;

    @Override
    protected Destino buildResult(final ExecutionContext ec) {

        checkParam("nombre", ec, String.class);
     
        
        return wrap(destinos).AgregarDestino(nombre);
    
    
    }
    
    
    
   
  




    @Inject Destinos destinos;

}
