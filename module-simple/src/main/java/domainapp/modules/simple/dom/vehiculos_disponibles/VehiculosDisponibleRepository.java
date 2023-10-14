package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculosDisponibleRepository extends JpaRepository<VehiculosDisponible, Long> {

    List<VehiculosDisponible> findByPatenteContaining(final String patente);

    VehiculosDisponible findByPatente(final String patente);



}
