package domainapp.modules.simple.dom.so;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByVehiculo(VehiculosDisponible vehiculo);
  
    List<Service> findByVehiculo_Name(String name);
}