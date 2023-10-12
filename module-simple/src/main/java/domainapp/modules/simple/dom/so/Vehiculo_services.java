package domainapp.modules.simple.dom.so;



import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import lombok.RequiredArgsConstructor;

@Collection                                             
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor                                
public class Vehiculo_services {                            
    private final Vehiculo vehiculo;                    

    public List<Service> coll() {
        return controlRepository.findByVehiculo(vehiculo);  
    }

    @Inject ServiceRepository controlRepository;                
}