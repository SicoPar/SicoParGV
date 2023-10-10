package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.Usuarios;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class SimpleObjectBuilder extends BuilderScriptWithResult<Usuario> {

    @Getter @Setter
    private String apellido;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String documento;
    @Getter @Setter
    private LocalDate fecha_nacimiento;
    @Getter @Setter
    private Sector sector;
    @Getter @Setter
    private String ciudad;
    @Getter @Setter
    private Genero genero;
    @Getter @Setter
    private Licencia licencia;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String telefono;

    @Override
    protected Usuario buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(usuarios).CrearUsuario(apellido,nombre,documento,fecha_nacimiento,sector,ciudad,genero,licencia,email,telefono);
    }

    // -- DEPENDENCIES

    @Inject Usuarios usuarios;

}
