package domainapp.modules.simple.dom.so;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {

	List<Viaje> findByVehiculoOrderByVisitAtDesc(Vehiculo vehiculo);
}