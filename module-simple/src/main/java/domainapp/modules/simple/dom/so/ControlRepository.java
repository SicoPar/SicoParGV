package domainapp.modules.simple.dom.so;



import java.util.List;

import org.springframework.data.repository.Repository;

public interface ControlRepository extends Repository<Control, Long> {

    List<Control> findByVehiculo(Vehiculo vehiculo);
}