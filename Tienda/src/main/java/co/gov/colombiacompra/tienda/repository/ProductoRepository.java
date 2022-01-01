package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Producto;

/**
 * Repositorio asociado a la entidad Producto
 * 
 * @author David.Murcia
 *
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
   
	/**
	 * Méteodo que busca un producto dado un id
	 * 
	 * @param idProducto identificador único asociado a un producto
	 * 
	 * @return Entidad Producto obtenida
	 * 
	 * @author David.Murcia
	 */
	Producto findByIdProducto(Long idProducto);
}
