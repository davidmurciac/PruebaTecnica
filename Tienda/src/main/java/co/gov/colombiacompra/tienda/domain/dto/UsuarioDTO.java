package co.gov.colombiacompra.tienda.domain.dto;

import java.io.Serializable;

import co.gov.colombiacompra.tienda.domain.enumeration.TipoIdentificacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1063933956227363511L;

	private Long idUsuario;
	
	private TipoIdentificacion tipoIdentificacion;
	
	private String numeroIdentificacion;
	
	private String primerNombre;
	
	private String segundoNombre;
	
	private String primerApellido;
	
	private String segundoApellido;
	
	private String email;
	
	private String rol;
	
	private String login;
	
	private String password;
	
	private Boolean activo;
	
	private String token;
	
}
