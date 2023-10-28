package domainapp.modules.simple.dom.destino;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

    List<Destino> findByNombreContaining(final String nombre);

    Destino findByNombre(final String nombre);

}
