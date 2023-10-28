package domainapp.modules.simple.dom.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByVehiculo(VehiculosDisponible vehiculo);
  
    List<Service> findByVehiculo_Patente(String patente);
    
    List<Service> findByActivo(boolean activo);

}