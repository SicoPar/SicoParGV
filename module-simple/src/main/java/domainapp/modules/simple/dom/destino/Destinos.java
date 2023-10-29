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

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Nombre_Control;
import domainapp.modules.simple.types.Nombre_Destino;

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
    		 @Nombre_Destino final String nombre) {
        return repositoryService.persist(Destino.withName(nombre));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Destino> findBydestinoLike(
    		 @Nombre_Destino final String nombre) {
        return repositoryService.allMatches(
                Query.named(Destino.class, Destino.NAMED_QUERY__FIND_BY_NAME_LIKE)
                     .withParameter("name", "%" + nombre + "%"));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Destino> findBydestino(
            @Nombre_Control final String nombre
            ) {
        return destinoRepository.findByNombreContainingAndActivo(nombre,true);
    }


    @Programmatic
    public Destino findByNombreAndActivo(final String nombre) {
        return destinoRepository.findByNombreAndActivo(nombre,true);
    }

    @Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	public List<Destino> ListaDeDestinosActivos() {
		 return destinoRepository.findByActivo(true);
	}

    @Programmatic
	public Destino findByNombreExact(final String nombre) {
		return destinoRepository.findByNombre(nombre);
	}



    @Programmatic
    public void ping() {
        jpaSupportService.getEntityManager(Destino.class)
            .ifSuccess(entityManager -> {
                final TypedQuery<Destino> q = entityManager.createQuery(
                        "SELECT p FROM Destino p ORDER BY p.nombre",
                        Destino.class)
                    .setMaxResults(1);
                q.getResultList();
            });
    }


	


}
