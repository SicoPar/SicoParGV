package domainapp.modules.simple.dom.so;

import java.util.Comparator;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

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


    Service(Vehiculo vehiculo, String name) {
        this.vehiculo = vehiculo;
        this.name = name;
    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "vehiculo_id")
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    @Getter @Setter
    private Vehiculo vehiculo;

    @Nombre_Control
    @Column(name = "nombre", length = Nombre_Control.MAX_LEN, nullable = false)
    @Getter @Setter
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String name;


    private final static Comparator<Service> comparator =
            Comparator.comparing(Service::getVehiculo).thenComparing(Service::getName);

    @Override
    public int compareTo(final Service other) {
        return comparator.compare(this, other);
    }


}