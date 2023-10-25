package domainapp.modules.simple.dom.so;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
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
//import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import domainapp.modules.simple.types.Nombre;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.types.Ciudad;
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Notes;
import domainapp.modules.simple.types.Telefono;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(schema = "simple", uniqueConstraints = {
		@UniqueConstraint(name = "Usuario__apellido__UNQ", columnNames = { "apellido" }) })
@NamedQueries({ @NamedQuery(name = Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE, query = "SELECT so " + "FROM Usuario so "
		+ "WHERE so.apellido LIKE :apellido") })
@EntityListeners(IsisEntityListener.class)
@DomainObject(logicalTypeName = "simple.Usuario", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Usuario implements Comparable<Usuario> {

	static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "Usuario.findByApellido";

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	@PropertyLayout(fieldSetId = "metadata", sequence = "999")
	@Getter
	@Setter
	private long version;

	public static Usuario withName(String apellido) {
		return withName(apellido, null,null,null,null,null,null,null,null,null);
	}

	public static Usuario withName(String apellido, String nombre,String documento ,LocalDate fecha_nacimiento,Sector sector,String ciudad,Genero genero,Licencia licencia, String email , String telefono) {
		val Usuario = new Usuario();
		Usuario.setApellido(apellido);
		Usuario.setNombre(nombre);
		Usuario.setDocumento(documento);
		Usuario.setFechaNacimiento(fecha_nacimiento);
		Usuario.setSector(sector);
		Usuario.setCiudad(ciudad);
		Usuario.setGenero(genero);
		Usuario.setLicencia(licencia);
		Usuario.setEmail(email);
		Usuario.setTelefono(telefono);
		return Usuario;
	}

	@Inject
	@Transient
	RepositoryService repositoryService;
	@Inject
	@Transient
	TitleService titleService;
	@Inject
	@javax.persistence.Transient
	MessageService messageService;

	@Title

	public String Titulito() {
		return getApellido() + (", " + getNombre() + "");
	}

	@Nombre
	@Column(length = Nombre.MAX_LEN, nullable = false)
	@Getter
	@Setter
	@ToString.Include
	@PropertyLayout(fieldSetId = "name", sequence = "2")
	private String nombre;

	@Name
	@Column(length = Name.MAX_LEN, nullable = false)
	@Getter
	@Setter
	@ToString.Include
	@PropertyLayout(fieldSetId = "name", sequence = "1")
	private String apellido;

	@Documento
	@Column(length = Documento.MAX_LEN, nullable = false)
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private String documento;
	
	
	@Column(name = "fecha_nacimiento", nullable = false)
	@Getter
	@Setter
	
	@PropertyLayout(fieldSetId = "name", sequence = "4")
	private LocalDate fechaNacimiento;
	
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "empresa", sequence = "1")     
	private Sector sector;
	
	
	@Ciudad
	@Column(length = Ciudad.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1.5")
	@Getter
	@Setter
	private String ciudad;	
	
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "name", sequence = "5")     
	private Genero genero;
	
	@Enumerated(EnumType.STRING)                                
	@Column(nullable = false)
	@Getter @Setter
	@PropertyLayout(fieldSetId = "empresa", sequence = "2")     
	private Licencia licencia;

	@Email
	@Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1.5")
	@Getter
	@Setter
	private String email;

	@Telefono
	@Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "contactDetails", sequence = "1.6")
	@Getter
	@Setter
	private String telefono;

	@Notes
	@javax.persistence.Column(length = Notes.MAX_LEN, nullable = true)
	@Getter
	@Setter
	@Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
	@PropertyLayout(fieldSetId = "notes", sequence = "4")
	private String notes;

	@Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
	@ActionLayout(associateWith = "apellido", promptStyle = PromptStyle.INLINE)
	public Usuario updateName(@Name final String apellido, @Nombre final String nombre) {
		setApellido(apellido);
		setNombre(nombre);
		return this;
	}

	public String default0UpdateName() {
		return getApellido();
	}

	public String default1UpdateName() {
		return getNombre();
	}

	public String validate0UpdateName(String newName) {
		for (char prohibitedCharacter : "&%$!".toCharArray()) {
			if (newName.contains("" + prohibitedCharacter)) {
				return "Character '" + prohibitedCharacter + "' is not allowed.";
			}
		}
		return null;
	}


	private final static Comparator<Usuario> comparator = Comparator.comparing(Usuario::getApellido);

	@Override
	public int compareTo(final Usuario other) {
		return comparator.compare(this, other);
	}

	
	@Action(
		    semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE,
		    commandPublishing = Publishing.ENABLED,
		    executionPublishing = Publishing.ENABLED
		)
		@ActionLayout(named = "Eliminar Usuario", position = ActionLayout.Position.PANEL)
		public void eliminarUsuario() {
		    final String titulo = titleService.titleOf(this);
		    messageService.informUser(String.format("'%s' ha sido eliminado", titulo));
		    repositoryService.removeAndFlush(this);
		}
	
	
//	
//	   @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
//	    @ActionLayout(
//	            associateWith = "simpleObject", position = ActionLayout.Position.PANEL,
//	            describedAs = "Deletes this object from the persistent datastore")
//	    public void delete() {
//	        final String title = titleService.titleOf(this);
//	        messageService.informUser(String.format("'%s' deleted", title));
//	        List<Viaje> viajesRelacionados = viajeRepository.findByVehiculosDisponible_Patente(patente);
//	        List<Service> servicesRelacionados = serviceRepository.findByVehiculo_Patente(patente);
//	  
//	        for (Viaje viaje : viajesRelacionados) {
//	            // Elimina cada viaje relacionado
//	           repositoryService.removeAndFlush(viaje);
//	        }
//	        
//	        for (Service service : servicesRelacionados) {
//	            // Elimina cada viaje relacionado
//	           repositoryService.removeAndFlush(service);
//	        }
//	        repositoryService.removeAndFlush(this);
//	    }
	
	
	
	
	
	
}