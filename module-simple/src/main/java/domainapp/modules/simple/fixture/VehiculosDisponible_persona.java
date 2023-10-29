package domainapp.modules.simple.fixture;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithBuilderScript;
import org.apache.isis.testing.fixtures.applib.personas.PersonaWithFinder;
import org.apache.isis.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import domainapp.modules.simple.dom.destino.Destino;
import domainapp.modules.simple.dom.destino.Destinos;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.usuario.Usuarios;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponibles;
import domainapp.modules.simple.enumeradores.Automovil;
import domainapp.modules.simple.enumeradores.Color;
import domainapp.modules.simple.enumeradores.Genero;
import domainapp.modules.simple.enumeradores.Licencia;
import domainapp.modules.simple.enumeradores.Sector;
import domainapp.modules.simple.enumeradores.TipoCombustible;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VehiculosDisponible_persona implements PersonaWithBuilderScript<VehiculosDisponibleBuilder>, PersonaWithFinder<VehiculosDisponible> {

	asd("Kop567", "Gol","Trend",Color.gris,Automovil.auto,TipoCombustible.Nafta,"1.6");

	private final String patente;
	private final String marca;
	private final String modelo;
	private final Color color;
	private final Automovil automovil;
	private final TipoCombustible combustible;
	private final String motor;
	

	@Override
	public VehiculosDisponibleBuilder builder() {
		return new VehiculosDisponibleBuilder().setPatente(patente).setMarca(marca).setModelo(modelo).setColor(color).setAutomovil(automovil).setCombustible(combustible).setMotor(motor);

	}
	

	
	
	
	;

	@Override
	public VehiculosDisponible findUsing(final ServiceRegistry serviceRegistry) {
		VehiculosDisponibles vehiculosDisponibles = serviceRegistry.lookupService(VehiculosDisponibles.class).orElse(null);
		return vehiculosDisponibles.findByPatenteExact(patente);
	}

	public static class PersistAll extends PersonaEnumPersistAll<VehiculosDisponible_persona, VehiculosDisponible> {

		public PersistAll() {
			super(VehiculosDisponible_persona.class);
		}
	}

}
