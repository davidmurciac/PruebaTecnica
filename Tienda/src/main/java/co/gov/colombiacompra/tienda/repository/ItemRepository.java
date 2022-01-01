package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Item;


/**
 * Repositorio asociado a la entidad Item
 * 
 * @author David.Murcia
 *
 */
@SuppressWarnings(value = "unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	/**
	 * Méteodo que busca un item dado un id
	 * 
	 * @param idItem identificador único en asociado a un ítems
	 * 
	 * @return Entidad Item obtenida
	 * 
	 * @author David.Murcia
	 */
	Item findByIdItem(Long idItem);

}
