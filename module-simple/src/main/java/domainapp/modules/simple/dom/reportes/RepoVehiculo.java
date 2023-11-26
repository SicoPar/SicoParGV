package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.TipoCombustible;

public class RepoVehiculo {	
	

	 	private String patente;
	    private String marca;
	    private String modelo;
	    private Color color;
	    private Automovil automovil;
	    private TipoCombustible combustible;	   
	    private String motor;
	   
	    

	    public RepoVehiculo(String patente, String marca, String modelo, Color color, Automovil automovil, TipoCombustible combustible, String motor){
	        this.patente = patente;
	        this.marca = marca;
	        this.modelo= modelo;
	        this.color= color;
	        this.automovil= automovil;
	        this.combustible= combustible;
	        this.motor= motor;	        
	    }

	    public RepoVehiculo(){}

	    public String getPatente(){ return this.patente;}
	    
	    public String getMarca() { return this.marca.toUpperCase();}

	    public String getModelo(){ return this.modelo;}
	    
	    public String getColorString(){ return this.color.name();}
	    
	    public Automovil getAutomovil(){ return this.automovil;}	    

	    public String getTipoCombustibleString() {return this.combustible.name();}

	    public String getMotor(){ return this.motor;}
	   
}
