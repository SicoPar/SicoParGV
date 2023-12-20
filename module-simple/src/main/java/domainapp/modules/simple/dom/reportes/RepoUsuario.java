package domainapp.modules.simple.dom.reportes;

import java.time.LocalDate;

import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;

public class RepoUsuario {
	
	 private String nombre;
	    private String apellido;
	    private String documento;
	    private String fechaNacimiento;
	    private Sector sector;
	    private String ciudad;
	    private Genero genero;
	    private Licencia licencia;
	    private String email;
	    private String telefono;
	    

	    public RepoUsuario(String nombre, String apellido, String documento, String fechaNacimiento, Sector sector, String ciudad, Genero genero, Licencia licencia, String email, String telefono){
	        this.nombre = nombre;
	        this.apellido = apellido;
	        this.documento= documento;
	        this.fechaNacimiento= fechaNacimiento;
	        this.sector= sector;
	        this.ciudad= ciudad;
	        this.genero= genero;
	        this.licencia= licencia;
	        this.email= email;
	        this.telefono = telefono;
	    }

	    public RepoUsuario(){}

	    public String getNombre(){ return this.nombre;}
	    
	    public String getApellido() { return this.apellido.toUpperCase();}

	    public String getDocumento(){ return this.documento;}
	    
	    public String getFechaNacimiento(){ return this.fechaNacimiento;}
	    
	    public Sector getSector(){ return this.sector;}	    

	    public String getCiudad() {return this.ciudad;}

	    public Genero getGenero(){ return this.genero;}
	    
	    public Licencia getLicencia(){ return this.licencia;}
	    
	    public String getEmail(){ return this.email;}
	    
	    public String getTelefono() { return this.telefono;}

}
