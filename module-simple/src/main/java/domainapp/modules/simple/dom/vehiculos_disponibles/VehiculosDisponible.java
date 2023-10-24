package domainapp.modules.simple.dom.vehiculos_disponibles;

import java.util.Comparator;

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
import domainapp.modules.simple.dom.so.Vehiculo;
import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.enumeradores.TipoCombustible;
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

    public static VehiculosDisponible withName(String patente, String marca , String modelo ,Color color,Automovil automovil,TipoCombustible combustible,String motor) {
        val simpleObject = new VehiculosDisponible();
        simpleObject.setPatente(patente);
        simpleObject.setMarca(marca);
        simpleObject.setModelo(modelo);
        simpleObject.setColor(color);
        simpleObject.setAutomovil(automovil);
        simpleObject.setCombustible(combustible);
        simpleObject.setMotor(motor);
        return simpleObject;
    }

    @Inject @javax.persistence.Transient RepositoryService repositoryService;
    @Inject @javax.persistence.Transient TitleService titleService;
    @Inject @javax.persistence.Transient MessageService messageService;



    

    
    
    @Title
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    private String patente;
    
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String Marca;
    
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "3")
    private String Modelo;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "4")     
	private Color color;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "5")     
	private Automovil automovil;
    
    @Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "6")     
	private TipoCombustible Combustible;
    
    @Patente
    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "7")
    private String Motor;

    @Notes
    @javax.persistence.Column(length = Notes.MAX_LEN, nullable = true)
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String notes;


    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "patente", promptStyle = PromptStyle.INLINE)
    public VehiculosDisponible updateName(
           @Patente final String patente) {
        setPatente(patente);
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


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            associateWith = "patente", position = ActionLayout.Position.PANEL,
            describedAs = "Deletes this object from the persistent datastore")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }



    private final static Comparator<VehiculosDisponible> comparator =
            Comparator.comparing(VehiculosDisponible::getPatente);

    @Override
    public int compareTo(final VehiculosDisponible other) {
        return comparator.compare(this, other);
    }

}



//package domainapp.modules.simple.dom.vehiculos_disponibles;
//
//import java.util.Comparator;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.persistence.Column;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
//
//import org.apache.isis.applib.annotation.Action;
//import org.apache.isis.applib.annotation.ActionLayout;
//import org.apache.isis.applib.annotation.DomainObject;
//import org.apache.isis.applib.annotation.DomainObjectLayout;
//import org.apache.isis.applib.annotation.PromptStyle;
//import org.apache.isis.applib.annotation.Property;
//import org.apache.isis.applib.annotation.PropertyLayout;
//import org.apache.isis.applib.annotation.Publishing;
//import org.apache.isis.applib.annotation.Title;
//import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
//import org.apache.isis.applib.services.message.MessageService;
//import org.apache.isis.applib.services.repository.RepositoryService;
//import org.apache.isis.applib.services.title.TitleService;
//import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;
//
//import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
//import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import lombok.val;
//import domainapp.modules.simple.dom.so.Vehiculo;
//import domainapp.modules.simple.enumeradores.Automovil;
//import domainapp.modules.simple.enumeradores.Color;
//import domainapp.modules.simple.enumeradores.Sector;
//import domainapp.modules.simple.enumeradores.TipoCombustible;
//import domainapp.modules.simple.types.Name;
//import domainapp.modules.simple.types.Notes;
//import domainapp.modules.simple.types.Patente;
//
//
//@javax.persistence.Entity
//@javax.persistence.Table(
//    schema="simple",
//    uniqueConstraints = {
//        @javax.persistence.UniqueConstraint(name = "VehiculosDisponible__patente__UNQ", columnNames = {"patente"})
//    }
//)
//@javax.persistence.NamedQueries({
//        @javax.persistence.NamedQuery(
//                name = VehiculosDisponible.NAMED_QUERY__FIND_BY_NAME_LIKE,
//                query = "SELECT so " +
//                        "FROM VehiculosDisponible so " +
//                        "WHERE so.patente LIKE :patente"
//        )
//})
//@javax.persistence.EntityListeners(IsisEntityListener.class)
//@DomainObject(logicalTypeName = "simple.VehiculosDisponible", entityChangePublishing = Publishing.ENABLED)
//@DomainObjectLayout()
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
//@ToString(onlyExplicitlyIncluded = true)
//public class VehiculosDisponible implements Comparable<VehiculosDisponible> {
//
//    static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "VehiculosDisponible.findBypatenteLike";
//
//    @javax.persistence.Id
//    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
//    @javax.persistence.Column(name = "id", nullable = false)
//    private Long id;
//
//    @javax.persistence.Version
//    @javax.persistence.Column(name = "version", nullable = false)
//    @PropertyLayout(fieldSetId = "metadata", sequence = "999")
//    @Getter @Setter
//    private long version;
//
//    public static VehiculosDisponible withName(String patente, String marca , String modelo ,Color color,Automovil automovil,TipoCombustible combustible,String motor) {
//        val simpleObject = new VehiculosDisponible();
//        simpleObject.setPatente(patente);
//        simpleObject.setMarca(marca);
//        simpleObject.setModelo(modelo);
//        simpleObject.setColor(color);
//        simpleObject.setAutomovil(automovil);
//        simpleObject.setCombustible(combustible);
//        simpleObject.setMotor(motor);
//        return simpleObject;
//    }
//
//    @Inject @javax.persistence.Transient RepositoryService repositoryService;
//    @Inject @javax.persistence.Transient TitleService titleService;
//    @Inject @javax.persistence.Transient MessageService messageService;
//
//
//
//    
////    @ManyToOne
////    @JoinColumn(name = "vehiculosDisponible_id")
////    private VehiculosDisponible vehiculosDisponible;
//    
//    
//    @Title
//    @Patente
//    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
//    @Getter @Setter @ToString.Include
//    @PropertyLayout(fieldSetId = "name", sequence = "1")
//    private String patente;
//    
//    @Patente
//    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
//    @Getter @Setter @ToString.Include
//    @PropertyLayout(fieldSetId = "name", sequence = "2")
//    private String Marca;
//    
//    @Patente
//    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
//    @Getter @Setter @ToString.Include
//    @PropertyLayout(fieldSetId = "name", sequence = "3")
//    private String Modelo;
//    
//    @Enumerated(EnumType.STRING)                                
//	@Column(nullable = false)
//	@Getter @Setter
//	@PropertyLayout(fieldSetId = "name", sequence = "4")     
//	private Color color;
//    
//    @Enumerated(EnumType.STRING)                                
//	@Column(nullable = false)
//	@Getter @Setter
//	@PropertyLayout(fieldSetId = "name", sequence = "5")     
//	private Automovil automovil;
//    
//    @Enumerated(EnumType.STRING)                                
//	@Column(nullable = false)
//	@Getter @Setter
//	@PropertyLayout(fieldSetId = "name", sequence = "6")     
//	private TipoCombustible Combustible;
//    
//    @Patente
//    @javax.persistence.Column(length = Patente.MAX_LEN, nullable = false)
//    @Getter @Setter @ToString.Include
//    @PropertyLayout(fieldSetId = "name", sequence = "7")
//    private String Motor;
//
//    @Notes
//    @javax.persistence.Column(length = Notes.MAX_LEN, nullable = true)
//    @Getter @Setter
//    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
//    @PropertyLayout(fieldSetId = "name", sequence = "2")
//    private String notes;
//
//
//    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
//    @ActionLayout(associateWith = "patente", promptStyle = PromptStyle.INLINE)
//    public VehiculosDisponible updateName(
//           @Patente final String patente) {
//        setPatente(patente);
//        return this;
//    }
//    public String default0UpdateName() {
//        return getPatente();
//    }
//    public String validate0UpdateName(String newName) {
//        for (char prohibitedCharacter : "&%$!".toCharArray()) {
//            if( newName.contains(""+prohibitedCharacter)) {
//                return "Character '" + prohibitedCharacter + "' is not allowed.";
//            }
//        }
//        return null;
//    }
//
//
//    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
//    @ActionLayout(
//            associateWith = "patente", position = ActionLayout.Position.PANEL,
//            describedAs = "Deletes this object from the persistent datastore")
//    public void delete() {
//        final String title = titleService.titleOf(this);
//        messageService.informUser(String.format("'%s' deleted", title));
//        repositoryService.removeAndFlush(this);
//    }
//
//    public String getPatenteVehiculoAsignado() {
//        return getPatente();
//    }
//
//    private final static Comparator<VehiculosDisponible> comparator =
//            Comparator.comparing(VehiculosDisponible::getPatente);
//
//    @Override
//    public int compareTo(final VehiculosDisponible other) {
//        return comparator.compare(this, other);
//    }
//
//}
