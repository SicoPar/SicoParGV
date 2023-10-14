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
import domainapp.modules.simple.dom.so.Service;
import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.UsuarioRepository;
import domainapp.modules.simple.dom.so.Usuarios;
import domainapp.modules.simple.dom.so.Vehiculo;
import domainapp.modules.simple.dom.so.VehiculoRepository;
import domainapp.modules.simple.dom.so.Viaje;
import domainapp.modules.simple.dom.so.ViajeRepository;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;

import java.util.stream.Collectors;

@DomainObject(nature = Nature.VIEW_MODEL, logicalTypeName = "simple.HomePageViewModel")
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

	public String title() {
		return getUsuarios().size() + " Usuarios";
	}

	public List<Usuario> getUsuarios() {
		return usuario.ListaDeUsuarios();
	}

	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.findAll();
	}
	public List<Destino> getDestinos() {
		return destino.listAll();
	}
	
	public List<Service> getServices() {
		return usuario.ListaDeServices();
	}
	
	public List<VehiculosDisponible> getVehiculosDisponibles() {
		return usuario.ListaDeVehiculosDisponibles();
	}
	
	public List<ViajePlusUsuario> getViaje() {
        return viajeRepository.findAll()
                .stream()
                .map(ViajePlusUsuario::new)
                .collect(Collectors.toList());
    }

	@Inject
	Usuarios usuario;
	@Inject
	Destinos destino;
	
	@Inject
	ViajeRepository viajeRepository;
	@Inject
	UsuarioRepository usuarioRepository;
	@Inject
	VehiculoRepository vehiculoRepository;


}
