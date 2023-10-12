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

import domainapp.modules.simple.types.Name;

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
            @Name final String name) {
        return repositoryService.persist(VehiculosDisponible.withName(name));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<VehiculosDisponible> findByNameLike(
            @Name final String name) {
        return repositoryService.allMatches(
                Query.named(VehiculosDisponible.class, VehiculosDisponible.NAMED_QUERY__FIND_BY_NAME_LIKE)
                     .withParameter("name", "%" + name + "%"));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<VehiculosDisponible> findByName(
            @Name final String name
            ) {
        return VehiculosDisponibleRepository.findByNameContaining(name);
    }


    @Programmatic
    public VehiculosDisponible findByNameExact(final String name) {
        return VehiculosDisponibleRepository.findByName(name);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<VehiculosDisponible> listAll() {
        return VehiculosDisponibleRepository.findAll();
    }




    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(VehiculosDisponible.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<VehiculosDisponible> q = entityManager.createQuery(
                        "SELECT p FROM SimpleObject p ORDER BY p.name",
                        VehiculosDisponible.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


}
