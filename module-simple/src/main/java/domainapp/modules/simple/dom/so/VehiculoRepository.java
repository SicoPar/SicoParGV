package domainapp.modules.simple.dom.so;


import java.util.List;

import org.springframework.data.repository.Repository;

public interface VehiculoRepository extends Repository<Vehiculo, Long> {

    List<Vehiculo> findByUsuario(Usuario usuario);
}