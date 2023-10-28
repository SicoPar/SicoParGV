package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.UsuarioRepository;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.enumeradores.TipoService;
import lombok.RequiredArgsConstructor;

@Action(
    semantics = SemanticsOf.IDEMPOTENT,
    commandPublishing = Publishing.ENABLED,
    executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "simple")
@RequiredArgsConstructor
public class VehiculoDisponible_AgregarService {
    private final VehiculosDisponible vehiculo;
    private Usuario usuario; // Usuario seleccionado desde el selector

    // Mantén este constructor, que recibe el vehículo y el usuario
    public VehiculoDisponible_AgregarService(VehiculosDisponible vehiculo, Usuario usuario) {
        this.vehiculo = vehiculo;
        this.usuario = usuario;
    }

    public Service act(Usuario usuario,TipoService tipo,LocalDate fecha, String kilometros,Riesgo riesgo) {
        return repositoryService.persist(new Service(vehiculo, usuario,tipo,fecha,kilometros,riesgo));
    }

    @Inject
    RepositoryService repositoryService;
    @Inject
    UsuarioRepository usuarioRepository;

    // Nuevo método para obtener la lista de usuarios disponibles
    public List<Usuario> choicesUsuario() {
        return usuarioRepository.findAll();
    }

 
}