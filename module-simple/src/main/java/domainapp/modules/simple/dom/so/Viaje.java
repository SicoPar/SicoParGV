package domainapp.modules.simple.dom.so;

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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;

import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.types.Razon;
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

	public Viaje(Usuario usuario, VehiculosDisponible vehiculosDisponible, Destino destino, LocalDate visitAt) {
		this.usuario = usuario;
		this.vehiculosDisponible = vehiculosDisponible;
		this.destino = destino;
		this.visitAt = visitAt;

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
	@JoinColumn(name = "usuario_id")
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private Usuario usuario;

	@Column(name = "visitAt", nullable = false)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "name", sequence = "2")
	private LocalDate visitAt;

//	@Razon
//	@Column(name = "razon", length = Razon.MAX_LEN, nullable = false)
//	@Getter
//	@Setter
//	@PropertyLayout(fieldSetId = "details", sequence = "1")
//	private String razon;

	private final static Comparator<Viaje> comparator = Comparator.comparing(Viaje::getVehiculosDisponible)
			.thenComparing(Viaje::getVisitAt);

	@Override
	public int compareTo(final Viaje other) {
		return comparator.compare(this, other);
	}

	@Inject
	@Transient
	TitleService titleService;

}