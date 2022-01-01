package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Carrito;
import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.enumeration.TipoIdentificacion;


/**
 * Repositorio asociado a la entidad Carrito
 * 
 * @author David.Murcia
 *
 */
@SuppressWarnings(value = "unused")
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
	
	/**
	 * Método que consulta el carrito dependiendo del estado de un usuario
	 * 
	 * @param usuario Usuario asociado al carrito buscado
	 * @param activo  Estado en el que se espera encontrar el carrito
	 * @return Entidad Usuario obtenida
	 * 
	 * @author David.Murcia
	 */
	public Carrito findByUsuarioAndActivo (Usuario usuario, Boolean activo);
	
}

