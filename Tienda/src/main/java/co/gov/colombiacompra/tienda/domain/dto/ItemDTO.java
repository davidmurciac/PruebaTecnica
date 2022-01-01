package co.gov.colombiacompra.tienda.domain.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2271168026902334849L;
	
	private Long idItem;
	
	private CarritoDTO carrito;
		
	private ProductoDTO producto;
	
	private Long cantidad;
		
}
