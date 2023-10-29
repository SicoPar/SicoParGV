package domainapp.modules.simple.dom.vehiculos_disponibles;

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

import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.TipoCombustible;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Patente;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.SimpleObjects"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class VehiculosDisponibles {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final VehiculosDisponibleRepository VehiculosDisponibleRepository;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public VehiculosDisponible create(
            @Patente final String patente,
             final String marca,
             final String modelo,
            final Color color,
            final Automovil automovil,
            final TipoCombustible combustible,
            final String motor
            ) {
        return repositoryService.persist(VehiculosDisponible.withName(patente,marca,modelo,color,automovil,combustible,motor));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<VehiculosDisponible> findByPatenteLike(
            @Name final String patente) {
        return repositoryService.allMatches(
                Query.named(VehiculosDisponible.class, VehiculosDisponible.NAMED_QUERY__FIND_BY_NAME_LIKE)
                     .withParameter("name", "%" + patente + "%"));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<VehiculosDisponible> findByName(
           @Patente final String patente
            ) {
        return VehiculosDisponibleRepository.findByPatenteContainingAndActivo(patente,true);
    }


    @Programmatic
    public VehiculosDisponible findByPatenteExact(final String patente) {
        return VehiculosDisponibleRepository.findByPatenteAndActivo(patente,true);
    }

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<VehiculosDisponible> ListaDeVehiculosDisponibleActivos() {
		 return VehiculosDisponibleRepository.findByActivo(true);
	}

//
//    @Action(semantics = SemanticsOf.SAFE)
//    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
//    public List<VehiculosDisponible> listAll() {
//        return VehiculosDisponibleRepository.findAll();
//    }




    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(VehiculosDisponible.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<VehiculosDisponible> q = entityManager.createQuery(
                        "SELECT p FROM SimpleObject p ORDER BY p.patente",
                        VehiculosDisponible.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


}
