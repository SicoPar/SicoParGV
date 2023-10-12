package domainapp.modules.simple.dom.so;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibleRepository;
import domainapp.modules.simple.types.Modelo;
import domainapp.modules.simple.types.Patente;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    schema="simple",
    uniqueConstraints = {
        @UniqueConstraint(name = "Pet__owner_name__UNQ", columnNames = {"owner_id, patente"})    }
)
@NamedQueries({ @NamedQuery(name = Vehiculo.NAMED_QUERY__FIND_BY_PATENTE, query = "SELECT so " + "FROM Vehiculo so "
		+ "WHERE so.patente LIKE :patente") })
@javax.persistence.EntityListeners(IsisEntityListener.class)
@Named("simple.Vehiculo")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Vehiculo implements Comparable<Vehiculo> {
	static final String NAMED_QUERY__FIND_BY_PATENTE = "Vehiculo.findByPatente";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "metadata", sequence = "1")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    @PropertyLayout(fieldSetId = "metadata", sequence = "999")
    @Getter @Setter
    private long version;

	@Inject
	@Transient
	VehiculosDisponibleRepository vehiculosDisponibleRepository;
    
    
    
    Vehiculo(Usuario usuario, String patente, String modelo, VehiculosDisponible vehiculosDisponible) {
        this.usuario = usuario;
        this.patente = patente;
        this.modelo = modelo;
        this.vehiculosDisponible = vehiculosDisponible;
       
    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    @Getter @Setter
    private Usuario usuario;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehiculosDisponible_id")
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    @Getter @Setter
    private VehiculosDisponible vehiculosDisponible;

    @Title
    @Patente
    @Column(name = "patente", length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String patente;
    
    @Modelo
    @Column(name = "modelo", length = Modelo.MAX_LEN, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "name", sequence = "3")
    private String modelo;


    private final static Comparator<Vehiculo> comparator =
            Comparator.comparing(Vehiculo::getUsuario).thenComparing(Vehiculo::getPatente);

    @Override
    public int compareTo(final Vehiculo other) {
        return comparator.compare(this, other);
    }
    

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "vehiculosDisponible", promptStyle = PromptStyle.INLINE)
    public Vehiculo updateVehiculosDisponible(
            final VehiculosDisponible vehiculosDisponible) {
        setVehiculosDisponible(vehiculosDisponible);
        return this;
    }

    @Programmatic
    public List<VehiculosDisponible> choices0UpdateVehiculosDisponible() {
        return vehiculosDisponibleRepository.findAll();
    }

    public VehiculosDisponible default0UpdateVehiculosDisponible() {
        return getVehiculosDisponible();
    }
    
   
}