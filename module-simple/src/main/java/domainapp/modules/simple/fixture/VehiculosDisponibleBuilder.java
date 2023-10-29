package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibles;
import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.enumeradores.TipoCombustible;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class VehiculosDisponibleBuilder extends BuilderScriptWithResult<VehiculosDisponible> {

    @Getter @Setter
    private String patente;
    @Getter @Setter
    private String marca;
    @Getter @Setter
    private String modelo;
    @Getter @Setter
    private Color color;
    @Getter @Setter
    private Automovil automovil;
    @Getter @Setter
    private TipoCombustible combustible;
    @Getter @Setter
    private String motor;
  

    
    


    @Override
    protected VehiculosDisponible buildResult(final ExecutionContext ec) {

        checkParam("patente", ec, String.class);
        checkParam("marca", ec, String.class);
        checkParam("modelo", ec, String.class);
        checkParam("color", ec, Color.class);
        checkParam("automovil", ec, Automovil.class);
        checkParam("combustible", ec, TipoCombustible.class);
        checkParam("motor", ec, String.class);
        return wrap(vehiculosDisponibles).create(patente, marca, modelo, color, automovil, combustible, motor);
    }

    // -- DEPENDENCIES

    @Inject VehiculosDisponibles vehiculosDisponibles;

}
