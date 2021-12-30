package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Item;

@SuppressWarnings(value = "unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Item findByIdItem(Long idItem);

}
