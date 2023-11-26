package domainapp.modules.simple.dom.reportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.value.Blob;

import domainapp.modules.simple.dom.service.Service;
import domainapp.modules.simple.dom.usuario.Usuario;
import domainapp.modules.simple.dom.vehiculos_disponibles.VehiculosDisponible;
import domainapp.modules.simple.dom.viaje.Viaje;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class EjecutarReportes {
			
	public Blob ListadoUsuarios(List<Usuario> usuarios) throws JRException, IOException{

		List<RepoUsuario> repoUsuarios= new ArrayList<>();
		repoUsuarios.add(new RepoUsuario());

        for (Usuario usuario : usuarios) {
        	RepoUsuario repoUsuario = new RepoUsuario(usuario.RepoNombre(), usuario.RepoApellido(),usuario.RepoDocumento(), usuario.RepoFechaNacimiento(), usuario.RepoSector(), usuario.RepoCiudad(), usuario.RepoGenero(), usuario.RepoLicencia(), usuario.RepoEmail(), usuario.RepoTelefono());
            repoUsuarios.add(repoUsuario);
        }
		
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoUsuarios);
        return GenerarArchivoPDF("Repo_Usuarios.jrxml", "Listado de Usuarios.pdf", ds);
    }
	
	
	public Blob ListadoVehiculosDisponibles(List<VehiculosDisponible> vehiculosDispo) throws JRException, IOException{

		List<RepoVehiculo> repoVehiculos= new ArrayList<>();
		repoVehiculos.add(new RepoVehiculo());

        for (VehiculosDisponible vehiculoDispo : vehiculosDispo) {
        	RepoVehiculo repoVehiculo = new RepoVehiculo(vehiculoDispo.RepoPatente(), vehiculoDispo.RepoMarca(), vehiculoDispo.RepoModelo(),vehiculoDispo.RepoColor(), vehiculoDispo.RepoAutomovil(), vehiculoDispo.RepoCombustible(), vehiculoDispo.RepoMotor());
        	repoVehiculos.add(repoVehiculo);
        }
		
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoVehiculos);
        return GenerarArchivoPDF("Repo_Vehiculos.jrxml", "Listado de Vehículos disponibles.pdf", ds);
    }
	
	public Blob ListadoViajes(List<Viaje> viajes) throws JRException, IOException{

		List<RepoViaje> repoViajes= new ArrayList<>();
		repoViajes.add(new RepoViaje());

        for (Viaje viaje : viajes) {
        	RepoViaje repoViaje = new RepoViaje(viaje.RepoUsuario(), viaje.RepoPasajero(), viaje.RepoVehiculosDisponible(), viaje.RepoDestino(), viaje.RepoRazon(), viaje.RepoFecha(), viaje.RepoRiesgo());
        	repoViajes.add(repoViaje);
        }
		
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoViajes);
        return GenerarArchivoPDF("Repo_Viajes.jrxml", "Listado de Viajes.pdf", ds);
    }


	public Blob listadoServices(List<Service> services) throws JRException, IOException{

		List<RepoService> repoServices= new ArrayList<>();
		repoServices.add(new RepoService());

        for (Service service : services) {
        	RepoService repoService = new RepoService(service.RepoVehiculosDisponible(), service.RepoUsuario(), service.RepoFecha(), service.RepoTipoService(), service.RepoKilometros(), service.RepoRiesgo());
        	repoServices.add(repoService);
        }
		
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoServices);
        return GenerarArchivoPDF("Repo_Services.jrxml", "Listado de Services.pdf", ds);
    }
	
    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException{

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);

    }

}
