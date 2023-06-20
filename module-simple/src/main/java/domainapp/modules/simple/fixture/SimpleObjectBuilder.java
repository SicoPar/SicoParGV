package domainapp.modules.simple.fixture;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.Usuarios;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class SimpleObjectBuilder extends BuilderScriptWithResult<Usuario> {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String documento;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String telefono;

    @Override
    protected Usuario buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(usuarios).create(name,nombre,documento,email,telefono);
    }

    // -- DEPENDENCIES

    @Inject Usuarios usuarios;

}
