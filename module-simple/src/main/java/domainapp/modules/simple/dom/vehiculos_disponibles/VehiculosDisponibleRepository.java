package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import domainapp.modules.simple.dom.destino.Destino;

public interface VehiculosDisponibleRepository extends JpaRepository<VehiculosDisponible, Long> {

    List<VehiculosDisponible> findByPatenteContainingAndActivo(final String patente,boolean activo);

    VehiculosDisponible findByPatenteAndActivo(final String patente,boolean activo);

    List<VehiculosDisponible> findByActivo(boolean activo);

}
