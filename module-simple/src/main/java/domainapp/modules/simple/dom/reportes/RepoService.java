package domainapp.modules.simple.dom.reportes;

import java.time.LocalDate;

import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.enumeradores.Riesgo;
import domainapp.modules.simple.enumeradores.TipoService;

public class RepoService {

	private VehiculosDisponible vehiculo;
    private Usuario usuario;
    private LocalDate fecha;
    private TipoService tipoService;
    private String kilometros;    	   
    private Riesgo riesgo;   
    

    public RepoService(VehiculosDisponible vehiculo, Usuario usuario, LocalDate fecha, TipoService tipoService, String kilometros, Riesgo riesgo){
        this.vehiculo = vehiculo;
        this.usuario = usuario;
        this.fecha= fecha;
        this.tipoService= tipoService;
        this.kilometros= kilometros;
        this.riesgo= riesgo;
    }

        public RepoService(){}

	    public String getVehiculosDisponibleString(){ return vehiculo.default0UpdateName();}
	    
	    public String getUsuarioString() { return usuario.Titulito();}

	    public String getFechaString(){ return this.fecha.toString();}
	    
	    public String getTipoServiceString(){ return this.tipoService.name();}
	    
	    public String getKilometrosString(){ return this.kilometros;}
	    
	    public String getRiesgoString(){ return this.riesgo.name();}
    
}
