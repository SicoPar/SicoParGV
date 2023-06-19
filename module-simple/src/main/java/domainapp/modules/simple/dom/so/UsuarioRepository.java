package domainapp.modules.simple.dom.so;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNameContaining(final String name);

    Usuario findByName(final String name);

}
