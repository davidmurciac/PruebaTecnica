package co.gov.colombiacompra.tienda.domain.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1647724149401611580L;

	private Long idProducto;
	
	private String nombre;
	
	private Long valorUnitario;
	
	private Long disponible;
	
}
