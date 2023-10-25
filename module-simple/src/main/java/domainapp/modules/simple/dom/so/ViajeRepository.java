package domainapp.modules.simple.dom.so;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {



	List<Viaje> findByVehiculosDisponibleOrderByVisitAtDesc(VehiculosDisponible vehiculosDisponible);
	 List<Viaje> findByVehiculosDisponible_Patente(String patente);
	   @Query("SELECT v FROM Viaje v WHERE v.vehiculosDisponible.patente = :patente AND v.visitAt = :fecha")
	    List<Viaje> findByPatenteAndFecha(@Param("patente") String patente, @Param("fecha") LocalDate fecha);
	   List<Viaje> findByUsuario_documento(String documento);
	   List<Viaje> findByDestino_name(String name);
}