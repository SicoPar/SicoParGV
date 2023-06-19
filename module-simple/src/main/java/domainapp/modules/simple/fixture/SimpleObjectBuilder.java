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

    @Override
    protected Usuario buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(usuarios).create(name);
    }

    // -- DEPENDENCIES

    @Inject Usuarios usuarios;

}
