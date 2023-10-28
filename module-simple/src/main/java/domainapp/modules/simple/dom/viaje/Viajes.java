package domainapp.modules.simple.dom.viaje;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PriorityPrecedence;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jpa.applib.services.JpaSupportService;

import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Nombre_Control;
import domainapp.modules.simple.types.Nombre_Destino;
import domainapp.modules.simple.types.Patente;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Viajes"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Viajes {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final ViajeRepository viajeRepository;


	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Listar Viajes por  Usuario")
	public List<Viaje> findByUsuario_documento(String documento) {
	    return viajeRepository.findByUsuario_documentoAndActivo(documento,true);
	}
	
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Listar Viajes por destino")
	public List<Viaje> findByDestino_nombre(@Nombre_Destino final String destino) {
	    return viajeRepository.findByDestino_nombre(destino);
	}
	
	

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Listar Viajes por Patente de Vehículo")
	public List<Viaje> listarViajesPorPatenteVehiculo(@Patente final String patente) {
	    return viajeRepository.findByVehiculosDisponible_Patente(patente);
	}
	
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Buscar Viajes por Patente de Vehículo y Fecha de Viaje")
	public List<Viaje> buscarViajesPorPatenteYFecha(String patente, LocalDate fecha) {
	    return viajeRepository.findByPatenteAndFecha(patente, fecha);
	}
	

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Viaje> ListaDeViajesActivos() {
		 return viajeRepository.findByActivo(true);
	}




    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(Viaje.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<Viaje> q = entityManager.createQuery(
                        "SELECT p FROM Destino p ORDER BY p.nombre",
                        Viaje.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


}
