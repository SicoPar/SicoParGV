package domainapp.modules.simple.dom.so;

import java.util.Comparator;

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
import domainapp.modules.simple.types.Documento;
import domainapp.modules.simple.types.Email;
import domainapp.modules.simple.types.Name;
import domainapp.modules.simple.types.Notes;
import domainapp.modules.simple.types.Telefono;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
		@UniqueConstraint(name = "Usuario__name__UNQ", columnNames = { "NAME" }) })
@NamedQueries({ @NamedQuery(name = Usuario.NAMED_QUERY__FIND_BY_NAME_LIKE, query = "SELECT so " + "FROM Usuario so "
		+ "WHERE so.name LIKE :name") })
@EntityListeners(IsisEntityListener.class)
@DomainObject(logicalTypeName = "simple.Usuario", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Usuario implements Comparable<Usuario> {

	static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "Usuario.findByName";

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

	public static Usuario withName(String name) {
		return withName(name, null,null,null,null);
	}

	public static Usuario withName(String name, String nombre,String documento , String email , String telefono) {
		val Usuario = new Usuario();
		Usuario.setName(name);
		Usuario.setNombre(nombre);
		Usuario.setDocumento(documento);
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
		return getName() + (", " + getNombre() + "");
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
	private String name;

	@Documento
	@Column(length = Documento.MAX_LEN, nullable = true)
	@PropertyLayout(fieldSetId = "name", sequence = "3")
	@Getter
	@Setter
	private String documento;

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
	@ActionLayout(associateWith = "name", promptStyle = PromptStyle.INLINE)
	public Usuario updateName(@Name final String name, @Nombre final String nombre) {
		setName(name);
		setNombre(nombre);
		return this;
	}

	public String default0UpdateName() {
		return getName();
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

	@Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
	@ActionLayout(associateWith = "name", position = ActionLayout.Position.PANEL, describedAs = "Deletes this object from the persistent datastore")
	public void delete() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		repositoryService.removeAndFlush(this);
	}

	private final static Comparator<Usuario> comparator = Comparator.comparing(Usuario::getName);

	@Override
	public int compareTo(final Usuario other) {
		return comparator.compare(this, other);
	}

}
