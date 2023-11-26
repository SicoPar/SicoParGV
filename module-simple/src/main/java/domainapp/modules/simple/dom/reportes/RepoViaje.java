package domainapp.modules.simple.dom.reportes;

import java.time.LocalDate;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.enumeradores.Riesgo;



public class RepoViaje {
	
	 	private Usuario usuario;
	    private Usuario pasajero;
	    private VehiculosDisponible vehiculosDisponible;
	    private Destino destino;
	    private String razon;
	    private LocalDate fecha;
	    private Riesgo riesgo;	     

	    public RepoViaje(Usuario usuario, Usuario pasajero, VehiculosDisponible vehiculosDisponible, Destino destino, String razon, LocalDate fecha, Riesgo riesgo){
	        this.usuario = usuario;
	        this.pasajero = pasajero;
	        this.vehiculosDisponible= vehiculosDisponible;
	        this.destino= destino;
	        this.razon= razon;
	        this.fecha= fecha;
	        this.riesgo= riesgo;	        
	    }

	    public RepoViaje(){}

	    public String getUsuarioString(){ return usuario.Titulito();}
	    
	    public Usuario getPasajero() { return this.pasajero;}

	    public String getVehiculosDisponibleString(){ return vehiculosDisponible.default0UpdateName();}
	    
	    public String getDestinoString(){ return destino.default0UpdateName();}
	    
	    public String getRazonString(){ return this.razon;}	    

	    public String getFechaString() {return this.fecha.toString();}

	    public String getRiesgoString(){ return this.riesgo.name();}

}
