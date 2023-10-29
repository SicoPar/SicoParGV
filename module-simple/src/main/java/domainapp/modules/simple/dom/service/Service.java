package domainapp.modules.simple.dom.service;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import java.time.LocalDate;
import java.util.Comparator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.enumeradores.TipoService;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Nombre_Control;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    schema="simple",
    uniqueConstraints = {
        @UniqueConstraint(name = "Vehiculo_name__UNQ", columnNames = {"vehiculo_id, nombre"})
    }
)
@javax.persistence.EntityListeners(IsisEntityListener.class)
@Named("controles.Control")
@DomainObject(entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Service implements Comparable<Service> {

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


    public Service(VehiculosDisponible vehiculo,Usuario usuario,TipoService tipo,LocalDate fecha,String kilometros,Riesgo riesgo) {
        this.vehiculo = vehiculo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.tipo = tipo;
        this.kilometros = kilometros;
        this.riesgo = riesgo;
        this.activo = true;
        
    }

    
    @Inject @javax.persistence.Transient RepositoryService repositoryService;
    @Inject @javax.persistence.Transient TitleService titleService;
    @Inject @javax.persistence.Transient MessageService messageService;

    
    
    
    
    
    

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehiculo_id")
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    @Getter @Setter
    private VehiculosDisponible vehiculo;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    @Getter @Setter
    private Usuario usuario;
    
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "details", sequence = "2")     
	private TipoService tipo;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "details", sequence = "2")     
	private Riesgo riesgo;
    
    @Column(name = "fecha", length = Nombre_Control.MAX_LEN, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private LocalDate fecha;
  
    @Column(name = "kilometros", length = Nombre_Control.MAX_LEN, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String kilometros;
    
    @Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1.7")
	@Getter
	@Setter
	private boolean activo;


    private final static Comparator<Service> comparator =
            Comparator.comparing(Service::getVehiculo).thenComparing(Service::getKilometros);
    
    
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            associateWith = "service", position = ActionLayout.Position.PANEL,
            describedAs = "Quieres elimnar este service?")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setActivo(false);
        
    }

    
    

    @Override
    public int compareTo(final Service other) {
        return comparator.compare(this, other);
    }


}