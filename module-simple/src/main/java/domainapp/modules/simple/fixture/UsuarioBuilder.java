package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class UsuarioBuilder extends BuilderScriptWithResult<Usuario> {

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

        checkParam("apellido", ec, String.class);
        checkParam("nombre", ec, String.class);
        checkParam("documento", ec, String.class);
        checkParam("fecha_nacimiento", ec, LocalDate.class);
        checkParam("sector", ec, Sector.class);
        checkParam("ciudad", ec, String.class);
        checkParam("genero", ec, Genero.class);
        checkParam("licencia", ec, Licencia.class);
        checkParam("email", ec, String.class);
        checkParam("email", ec, String.class);
        checkParam("licencia", ec, String.class);
        return wrap(usuarios).CrearUsuario(apellido,nombre,documento,fecha_nacimiento,sector,ciudad,genero,licencia,email,telefono);
    }

    // -- DEPENDENCIES

    @Inject Usuarios usuarios;

}
