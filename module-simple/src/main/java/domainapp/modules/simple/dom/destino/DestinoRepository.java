package domainapp.modules.simple.dom.destino;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import domainapp.modules.simple.dom.usuario.Usuario;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

    List<Destino> findByNombreContainingAndActivo(final String nombre,boolean activo);

//    Destino findByNombreAndActivo(final String nombre,boolean activo);
    List<Destino> findByActivo(boolean activo);
    Destino findByNombre(final String nombre);
    

	Destino findByNombreAndActivo(String nombre, boolean activo);
}
