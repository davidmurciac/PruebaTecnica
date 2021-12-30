package co.gov.colombiacompra.tienda.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.gov.colombiacompra.tienda.domain.enumeration.Rol;
import co.gov.colombiacompra.tienda.domain.enumeration.TipoIdentificacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USUARIO",
uniqueConstraints = { @UniqueConstraint(columnNames = { "TIPO_IDENTIFICACION", "NUMERO_IDENTIFICACION" }) })
public class Usuario implements Serializable{
	
	/**
	 * @serialField 
	 */
	private static final long serialVersionUID = -4888826414695245948L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUsuario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_IDENTIFICACION", nullable = false)
	private TipoIdentificacion tipoIdentificacion;
	
	@Column(name = "NUMERO_IDENTIFICACION", nullable = false)
	private String numeroIdentificacion;
	
	@Column(name = "PRIMER_NOMBRE", length = 50, nullable = false)
	private String primerNombre;
	
	@Column(name="SEGUNDO_NOMBRE", length = 50)
	private String segundoNombre;
	
	@Column(name = "PRIMER_APELLIDO", length = 50, nullable = false)
	private String primerApellido;
	
	@Column(name = "SEGUNDO_APELLIDO", length = 50)
	private String segundoApellido;
	
	@Column(name = "EMAIL", length = 100)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ROL", length = 15, nullable = false)
	private Rol rol = Rol.REGULAR;
	
	@Column(unique = true, name = "LOGIN",length = 30, nullable = false)
	private String login;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "ACTIVO", nullable = false)
	private Boolean activo = true ;
	
	public String getNombre() {
		return this.primerNombre + this.segundoApellido + this.primerApellido + this.segundoApellido;
	}
	
	
	
}
