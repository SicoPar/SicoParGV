package domainapp.modules.simple.dom.so;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByVehiculo(Vehiculo vehiculo);
  
    List<Service> findByVehiculo_Patente(String patente);
}