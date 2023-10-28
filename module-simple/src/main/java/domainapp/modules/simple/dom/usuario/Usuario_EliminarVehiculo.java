//package domainapp.modules.simple.dom.so;
//
//import javax.inject.Inject;
//
//import org.apache.isis.applib.annotation.Action;
//import org.apache.isis.applib.annotation.ActionLayout;
//import org.apache.isis.applib.annotation.Publishing;
//import org.apache.isis.applib.annotation.SemanticsOf;
//import org.apache.isis.applib.services.repository.RepositoryService;
//
//import domainapp.modules.simple.types.Patente;
//import lombok.RequiredArgsConstructor;
//
//@Action(
//        semantics = SemanticsOf.IDEMPOTENT,
//        commandPublishing = Publishing.ENABLED,
//        executionPublishing = Publishing.ENABLED
//)
//@ActionLayout(associateWith = "simple", sequence = "2")
//@RequiredArgsConstructor
//public class Usuario_EliminarVehiculo {
//
//    private final Usuario usuario;
//
//    public Usuario act(@Patente final String patente) {
//    	vehiculoRepository.findByUsuarioAndPatente(usuario, patente)
//                .ifPresent(vehiculo -> repositoryService.remove(vehiculo));
//        return usuario;
//    }
//
//    @Inject VehiculoRepository vehiculoRepository;
//    @Inject RepositoryService repositoryService;
////}
package domainapp.modules.simple.dom.usuario;

