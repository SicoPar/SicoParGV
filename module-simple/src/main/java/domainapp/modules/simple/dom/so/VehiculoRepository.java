


package domainapp.modules.simple.dom.so;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;


public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByUsuario(Usuario usuario);

    Optional<Vehiculo> findByUsuarioAndPatente(Usuario usuario, String patente);
    Optional<Vehiculo> findByUsuarioAndVehiculosDisponible(Usuario usuario, VehiculosDisponible vehiculosDisponible);
    
    List<Vehiculo> findByVehiculosDisponible(VehiculosDisponible vehiculosDisponible);
    
    public List<Vehiculo> findByVehiculosDisponible_Patente(final String patente);
}