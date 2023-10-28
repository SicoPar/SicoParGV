package domainapp.modules.simple.dom.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    <optional>List<Usuario> findByApellidoContainingAndActivo(final String apellido,boolean activo);

    Usuario findByApellido(final String apellido);
    List<Usuario> findByActivo(boolean activo);
}
