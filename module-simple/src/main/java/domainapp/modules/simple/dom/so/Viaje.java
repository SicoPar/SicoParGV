package domainapp.modules.simple.dom.so;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.Property;

import domainapp.modules.simple.enumeradores.Estado;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Nombre;
import domainapp.modules.simple.types.Razon;
import domainapp.modules.simple.types.prueba;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "simple", name = "Viaje", uniqueConstraints = {
		@UniqueConstraint(name = "Viaje__vehiculosDisponible_visitAt__UNQ", columnNames = { "vehiculoDisponible_id",
				"name" }) })
@EntityListeners(IsisEntityListener.class)
@DomainObject(logicalTypeName = "simple.Viaje", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Viaje implements Comparable<Viaje> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "metadata", sequence = "1")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	@PropertyLayout(fieldSetId = "metadata", sequence = "999")
	@Getter
	@Setter
	private long version;

	public Viaje(Usuario usuario,@prueba Usuario pasajero, VehiculosDisponible vehiculosDisponible, Destino destino,String razon, LocalDate visitAt,Riesgo riesgo,Estado estado) {
		this.usuario = usuario;
		this.pasajero = pasajero;
		this.vehiculosDisponible = vehiculosDisponible;
		this.destino = destino;
		this.razon = razon;
		this.visitAt = visitAt;
		this.riesgo = riesgo;
		this.estado = estado;
		this.activo = true;

	}

	public String title() {
		return titleService.titleOf(getVehiculosDisponible()) + " @ "
				+ getVisitAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "vehiculosDisponible")
	@PropertyLayout(fieldSetId = "name", sequence = "1")
	@Getter @Setter
	private VehiculosDisponible vehiculosDisponible;

	@ManyToOne(optional = false)
	@JoinColumn(name = "destino_id")
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private Destino destino;
	

	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id",nullable = true)
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private Usuario usuario;
	
	

	
	
	
	
	@prueba
	@ManyToOne(optional = true)
	@JoinColumn(name = "pasajero_id", nullable = true)
	@PropertyLayout(fieldSetId = "name", sequence = "4")
	@Getter
	@Setter
	private Usuario pasajero;
	
	
	
	@prueba
	@Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1.6")
	@Getter
	@Setter
	private String prueba;

	@Column(name = "visitAt", nullable = false)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "name", sequence = "2")
	private LocalDate visitAt;

	@Razon
	@Column(name = "razon", length = Razon.MAX_LEN, nullable = true)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "details", sequence = "1")
	@Property(editing = Editing.ENABLED, optionality = Optionality.OPTIONAL)
	private String razon;
	
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "details", sequence = "2")     
	private Riesgo riesgo;
	
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "details", sequence = "2")     
	private Estado estado;
	
	
	@Column(name = "activo", length = Razon.MAX_LEN, nullable =false)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "details", sequence = "1")
	private boolean activo;

	private final static Comparator<Viaje> comparator = Comparator.comparing(Viaje::getVehiculosDisponible)
			.thenComparing(Viaje::getVisitAt);

	@Override
	public int compareTo(final Viaje other) {
		return comparator.compare(this, other);
	}
	
	
	@Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
	@ActionLayout(associateWith = "Viaje", promptStyle = PromptStyle.INLINE)
	public Viaje updateEstado(Estado estado) {
		setEstado(estado);
		return this;
	}

	@Inject
	@Transient
	TitleService titleService;

}