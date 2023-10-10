package domainapp.modules.simple.dom.destino;

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

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Destinos"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Destinos {

    final RepositoryService repositoryService;
    final JpaSupportService jpaSupportService;
    final DestinoRepository destinoRepository;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Destino AgregarDestino(
            @Name final String name) {
        return repositoryService.persist(Destino.withName(name));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Destino> findByNameLike(
            @Name final String name) {
        return repositoryService.allMatches(
                Query.named(Destino.class, Destino.NAMED_QUERY__FIND_BY_NAME_LIKE)
                     .withParameter("name", "%" + name + "%"));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Destino> findByName(
            @Nombre_Control final String nombre
            ) {
        return destinoRepository.findByNameContaining(nombre);
    }


    @Programmatic
    public Destino findByNameExact(final String nombre) {
        return destinoRepository.findByName(nombre);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Destino> listAll() {
        return destinoRepository.findAll();
    }




    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(Destino.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<Destino> q = entityManager.createQuery(
                        "SELECT p FROM Destino p ORDER BY p.name",
                        Destino.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


}
