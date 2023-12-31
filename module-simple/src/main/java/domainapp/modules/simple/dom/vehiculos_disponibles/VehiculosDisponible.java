package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.service.ServiceRepository;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.enumeradores.TipoCombustible;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Modelo;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Notes;
import domainapp.modules.simple.types.Patente;


@javax.persistence.Entity
@javax.persistence.Table(
    schema="simple",
    uniqueConstraints = {
        @javax.persistence.UniqueConstraint(name = "VehiculosDisponible__patente__UNQ", columnNames = {"patente"})
    }
)
@javax.persistence.NamedQueries({
        @javax.persistence.NamedQuery(
                name = VehiculosDisponible.NAMED_QUERY__FIND_BY_NAME_LIKE,
                query = "SELECT so " +
                        "FROM VehiculosDisponible so " +
                        "WHERE so.patente LIKE :patente"
        )
})
@javax.persistence.EntityListeners(IsisEntityListener.class)
@DomainObject(logicalTypeName = "simple.VehiculosDisponible", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class VehiculosDisponible implements Comparable<VehiculosDisponible> {

    static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "VehiculosDisponible.findBypatenteLike";

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id", nullable = false)
    private Long id;

    @javax.persistence.Version
    @javax.persistence.Column(name = "version", nullable = false)
    @PropertyLayout(fieldSetId = "metadata", sequence = "999")
    @Getter @Setter
    private long version;

    public String RepoPatente(){ return this.patente;}
    public String RepoMarca(){ return this.marca;}
    public String RepoModelo(){ return this.modelo;}
    public Color RepoColor(){ return this.color;}
    public Automovil RepoAutomovil() { return this.automovil;}
    public TipoCombustible RepoCombustible() {return this.combustible;}
    public String RepoMotor() {return this.motor;}
    

    public static VehiculosDisponible withName(String patente, String marca , String modelo ,Color color,Automovil automovil,TipoCombustible combustible,String motor) {
        val simpleObject = new VehiculosDisponible();
        simpleObject.setPatente(patente);
        simpleObject.setMarca(marca);
        simpleObject.setModelo(modelo);
        simpleObject.setColor(color);
        simpleObject.setAutomovil(automovil);
        simpleObject.setCombustible(combustible);
        simpleObject.setMotor(motor);
        simpleObject.setActivo(true);
        return simpleObject;
    }

    @Inject @javax.persistence.Transient RepositoryService repositoryService;
    @Inject @javax.persistence.Transient ViajeRepository viajeRepository;
    @Inject @javax.persistence.Transient ServiceRepository serviceRepository;
    @Inject @javax.persistence.Transient TitleService titleService;
    @Inject @javax.persistence.Transient MessageService messageService;
   


    

    
    
    @Title
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "1.1")
    private String patente;
    
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "1.2")
    private String marca;
    
    @Modelo
    @Column(length = Modelo.MAX_LEN, nullable = true)
    @PropertyLayout(fieldSetId = "name", sequence = "1.3")
    @Getter @Setter
    private String modelo;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "2")     
	private Color color;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "3")     
	private Automovil automovil;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "4")     
	private TipoCombustible combustible;
    
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "5")
    private String motor;
    
    @Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "6")
	@Getter
	@Setter
	private boolean activo;

  

    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "simpleObject", promptStyle = PromptStyle.DIALOG_MODAL)
    public VehiculosDisponible updateName(
           @Patente final String patente,String modelo,Color color,Automovil automovil,TipoCombustible combustible,String marca,String motor) {
        setPatente(patente);
        setModelo(modelo);
        setColor(color);
        setAutomovil(automovil);
        setCombustible(combustible);
        setMarca(marca);
        setMotor(motor);

        return this;
    }
    public String default0UpdateName() {
        return getPatente();
    }    
    
    
    public String validate0UpdateName(String newName) {
        for (char prohibitedCharacter : "&%$!".toCharArray()) {
            if( newName.contains(""+prohibitedCharacter)) {
                return "Character '" + prohibitedCharacter + "' is not allowed.";
            }
        }
        return null;
    } 

    private final static Comparator<VehiculosDisponible> comparator =
            Comparator.comparing(VehiculosDisponible::getPatente);

    @Override
    public int compareTo(final VehiculosDisponible other) {
        return comparator.compare(this, other);
    }

}



