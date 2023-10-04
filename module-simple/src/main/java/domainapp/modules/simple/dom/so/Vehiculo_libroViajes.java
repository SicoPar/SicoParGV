package domainapp.modules.simple.dom.so;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.simple.types.Razon;
import lombok.RequiredArgsConstructor;

@Action(semantics = SemanticsOf.IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
@ActionLayout(associateWith = "simple", sequence = "1")
@RequiredArgsConstructor
public class Vehiculo_libroViajes {

	private final Vehiculo vehiculo;

	public Viaje act(LocalDateTime visitAt) {
		return repositoryService.persist(new Viaje(vehiculo, visitAt));
	}

	public String validate0Act(LocalDateTime visitAt) {
		return clockService.getClock().nowAsLocalDateTime().isBefore(visitAt) ? null : "Must be in the future";
	}

	public LocalDateTime default0Act() {
		return clockService.getClock().nowAsLocalDateTime().toLocalDate().plusDays(1).atTime(LocalTime.of(9, 0));
	}

	@Inject
	ClockService clockService;
	@Inject
	RepositoryService repositoryService;
}


