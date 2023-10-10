package domainapp.modules.simple.dom.so;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
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
import domainapp.modules.simple.types.Razon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "simple", name = "Viaje", uniqueConstraints = {
		@UniqueConstraint(name = "Viaje__vehiculo_visitAt__UNQ", columnNames = { "vehiculo_id", "name" }) })
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

	 public Viaje(Vehiculo vehiculo,Destino destino, LocalDateTime visitAt) {
		this.vehiculo = vehiculo;
		this.destino = destino;
		this.visitAt = visitAt;
		
	}

	public String title() {
		return titleService.titleOf(getVehiculo()) + " @ "
				+ getVisitAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	}
	
	

	@ManyToOne(optional = false)
	@JoinColumn(name = "vehiculo_id")
	@PropertyLayout(fieldSetId = "name", sequence = "1")
	@Getter
	@Setter
	private Vehiculo vehiculo;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "destino_id")
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private Destino destino;

	@Column(name = "visitAt", nullable = false)
	@Getter
	@Setter
	@PropertyLayout(fieldSetId = "name", sequence = "2")
	private LocalDateTime visitAt;

//	@Razon
//	@Column(name = "razon", length = Razon.MAX_LEN, nullable = false)
//	@Getter
//	@Setter
//	@PropertyLayout(fieldSetId = "details", sequence = "1")
//	private String razon;

	private final static Comparator<Viaje> comparator = Comparator.comparing(Viaje::getVehiculo)
			.thenComparing(Viaje::getVisitAt);

	@Override
	public int compareTo(final Viaje other) {
		return comparator.compare(this, other);
	}
	


	@Inject
	@Transient
	TitleService titleService;
  
}