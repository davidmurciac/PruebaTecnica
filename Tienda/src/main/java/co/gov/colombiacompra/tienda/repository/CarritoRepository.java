package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Carrito;
import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.enumeration.TipoIdentificacion;

@SuppressWarnings(value = "unused")
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
	
	public Carrito findByUsuarioAndActivo (Usuario usuario, Boolean activo);
	
}

