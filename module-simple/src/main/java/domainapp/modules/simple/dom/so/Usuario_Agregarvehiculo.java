package domainapp.modules.simple.dom.so;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibleRepository;
import domainapp.modules.simple.types.Modelo;
import domainapp.modules.simple.types.Patente;
import lombok.RequiredArgsConstructor;

@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@ActionLayout(associateWith = "simple")
@RequiredArgsConstructor
public class Usuario_Agregarvehiculo {

    private final Usuario usuario;

    @javax.inject.Inject
    private RepositoryService repositoryService;
    @javax.inject.Inject
    private VehiculoRepository vehiculoRepository;

    @Inject
    private VehiculosDisponibleRepository vehiculosDisponibleRepository;
//    private Repository vehiculosDisponibleRepository;

    public Usuario act(
            @Patente final String patente,
            @Modelo final String modelo,
            VehiculosDisponible vehiculosDisponible
    ) {
        repositoryService.persist(new Vehiculo(usuario, patente, modelo, vehiculosDisponible));
        return usuario;
    }

    public List<VehiculosDisponible> choices2Act() {
        return vehiculosDisponibleRepository.findAll();
    }

    public VehiculosDisponible default2Act() {
        // Esto establecerá un valor predeterminado si lo deseas
        return vehiculosDisponibleRepository.findAll().stream().findFirst().orElse(null);
    }

    public String validateAct(
            final String patente,
            final String modelo,
            final VehiculosDisponible vehiculosDisponible
    ) {
        if (vehiculosDisponible == null) {
            return "Debes seleccionar un vehículo disponible";
        }
        if (vehiculoRepository.findByUsuarioAndPatente(usuario, patente).isPresent()) {
            return String.format("El vehículo con la patente ingresada ya está definido por este usuario: %s", patente);
        }
        if (vehiculoRepository.findByUsuarioAndVehiculosDisponible(usuario, vehiculosDisponible).isPresent()) {
            return String.format("Este usuario ya ha asignado este vehículo disponible: %s", vehiculosDisponible.getPatente());
        }

       return null;
    }
}