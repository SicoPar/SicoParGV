package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;

import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import lombok.RequiredArgsConstructor;

@Collection
@CollectionLayout(defaultView = "table")
@RequiredArgsConstructor

public class VehiculosDisponible_viajes {
    private final VehiculosDisponible vehiculosDisponible;

    public List<Viaje> coll() {
        if (viajeRepository != null) {
            return viajeRepository.findByVehiculosDisponibleOrderByVisitAtDesc(vehiculosDisponible);
        } else {
            throw new IllegalStateException("viajeRepository es nulo");
        }
    }

    @Inject
    ViajeRepository viajeRepository;
}


