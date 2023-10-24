package domainapp.webapp.application.services.homepage;



import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Projecting;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.so.Usuario;
import domainapp.modules.simple.dom.so.Vehiculo;
import domainapp.modules.simple.dom.so.Viaje;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import lombok.Getter;
import lombok.NoArgsConstructor;



@DomainObject(nature=Nature.VIEW_MODEL, logicalTypeName = "simple.ViajePlusUsuario")
@DomainObjectLayout(named = "Viaje")
@XmlRootElement
@NoArgsConstructor
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ViajePlusUsuario {

    @Property(
            projecting = Projecting.PROJECTED,
            hidden = Where.EVERYWHERE
    )
    @Getter
    private Viaje viaje;


    ViajePlusUsuario(Viaje viaje) {this.viaje = viaje;}

   public VehiculosDisponible getVehiculoDisponible() {return viaje.getVehiculosDisponible();}
   public Usuario getUsuario() {return viaje.getUsuario();}
    public Destino getDestino() {return viaje.getDestino();}

    public LocalDate getViajeAt() {return viaje.getVisitAt();}

//    public Usuario getUsuario() {
//        return getVehiculo().getUsuario();
//    }
}