package co.gov.colombiacompra.tienda.domain.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CarritoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6460163834169047385L;

	private Long idCarrito;
	
	private UsuarioDTO usuario;
	
	private Set<ItemDTO> items;
	
	private Boolean activo;

			
}
