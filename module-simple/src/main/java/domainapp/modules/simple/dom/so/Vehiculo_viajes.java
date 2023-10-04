package domainapp.modules.simple.dom.so;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import lombok.RequiredArgsConstructor;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor

public class Vehiculo_viajes {
    private final Vehiculo vehiculo;

    public List<Viaje> coll() {
        if (viajeRepository != null) {
            return viajeRepository.findByVehiculoOrderByVisitAtDesc(vehiculo);
        } else {
            throw new IllegalStateException("viajeRepository es nulo");
        }
    }

    @Inject
    ViajeRepository viajeRepository;
}



