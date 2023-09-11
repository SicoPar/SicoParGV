package domainapp.modules.simple.dom.so;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByUsuario(Usuario usuario);

    Optional<Vehiculo> findByUsuarioAndPatente(Usuario usuario, String patente);
}