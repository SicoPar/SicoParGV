package domainapp.modules.simple.dom.service;

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
        logicalTypeName = "simple.Services"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Services {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final ServiceRepository serviceRepository;




	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Service> ListaDeServicesActivos() {
		 return serviceRepository.findByActivo(true);
	}

//	@Action(semantics = SemanticsOf.SAFE)
//	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
//	public List<Service> ListaDeServices() {
//		return serviceRepository.findAll();
//	}
	


	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, named = "Listar Servicios por Patente de Vehículo")
	public List<Service> listarServiciosPorPatenteVehiculo(@Patente final String patente) {
	    return serviceRepository.findByVehiculo_Patente(patente);
	}


    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(Service.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<Service> q = entityManager.createQuery(
                        "SELECT p FROM Destino p ORDER BY p.nombre",
                        Service.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


}
