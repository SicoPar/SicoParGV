package domainapp.modules.simple.dom.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    <optional>List<Usuario> findByApellidoContaining(final String apellido);

    Usuario findByApellido(final String apellido);
    List<Usuario> findByActivo(boolean activo);
}
