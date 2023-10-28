package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.destino.Destinos;
import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.service.Services;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.UsuarioRepository;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibles;
import domainapp.modules.simple.dom.viaje.Viaje;
import domainapp.modules.simple.dom.viaje.ViajeRepository;
import domainapp.modules.simple.dom.viaje.Viajes;

import java.util.stream.Collectors;

@DomainObject(nature = Nature.VIEW_MODEL, logicalTypeName = "simple.HomePageViewModel")
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

	public String title() {
		return getUsuarios().size() + " Usuarios";
	}

	public List<Usuario> getUsuarios() {
		return usuario.ListaDeUsuariosActivos();
	}

	public List<Destino> getDestinos() {
		return destino.ListaDeDestinosActivos();
	}
	
	public List<Service> getServices() {
		return service.ListaDeServicesActivos();
	}
	
	public List<VehiculosDisponible> getVehiculosDisponibles() {
		return vehiculos.ListaDeVehiculosDisponibleActivos();
	}
	
	public List<ViajePlusUsuario> getViaje() {
        return viaje.ListaDeViajesActivos()
                .stream()
                .map(ViajePlusUsuario::new)
                .collect(Collectors.toList());
    }

	@Inject
	Usuarios usuario;
	@Inject
	VehiculosDisponibles vehiculos;
	
	@Inject
	Services service;
	@Inject
	Destinos destino;
	@Inject
	Viajes viaje;
	@Inject
	ViajeRepository viajeRepository;
	@Inject
	UsuarioRepository usuarioRepository;
	


}