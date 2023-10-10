package domainapp.webapp.application.services.homepage;




import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.wrapper.WrapperFactory;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.DestinoRepository;
import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.UsuarioRepository;
import domainapp.modules.simple.dom.so.Vehiculo;
import domainapp.modules.simple.dom.so.VehiculoRepository;
import domainapp.modules.simple.dom.so.Vehiculo_libroViajes;
import domainapp.modules.simple.dom.so.Viaje;
import lombok.RequiredArgsConstructor;



@Action
@RequiredArgsConstructor
public class HomePageViewModel_libroViajes {

    final HomePageViewModel homePageViewModel;
    

    public Object act(Usuario usuario, Vehiculo vehiculo,Destino destino, LocalDateTime visitAt, boolean showVisit) {
        Viaje viaje = wrapperFactory.wrapMixin(Vehiculo_libroViajes.class, vehiculo).act(destino,visitAt);
        return showVisit ? viaje : homePageViewModel;
    }
    public List<Usuario> autoComplete0Act(final String apellido) {
        return usuarioRepository.findByApellidoContaining(apellido);
    }
    
  public List<Destino> autoComplete2Act(final String name) {
  return destinoRepository.findByNameContaining(name);
}
//    public List<Destino> autoComplet0Act(final String nombre) {
//        List<Destino> destinos = destinoRepository.findByNameContaining(nombre);
//        return destinos != null ? destinos : Collections.emptyList();
//    }
    public List<Vehiculo> choices1Act(Usuario usuario) {
        if(usuario == null) return Collections.emptyList();
        return vehiculoRepository.findByUsuario(usuario);
    }
    public LocalDateTime default2Act(Usuario usuario, Vehiculo vehiculo) {
        if(vehiculo == null) return null;
        return factoryService.mixin(Vehiculo_libroViajes.class, vehiculo).default0Act();
    }
    public String validate2Act(Usuario usuario, Vehiculo vehiculo, LocalDateTime viajeAt){
         return factoryService.mixin(Vehiculo_libroViajes.class, vehiculo).validate0Act(viajeAt);
    }

    @Inject VehiculoRepository vehiculoRepository;
    @Inject UsuarioRepository usuarioRepository;
    @Inject DestinoRepository destinoRepository;
    @Inject WrapperFactory wrapperFactory;
    @Inject FactoryService factoryService;
}