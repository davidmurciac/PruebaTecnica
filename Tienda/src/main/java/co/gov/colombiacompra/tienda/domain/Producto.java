package co.gov.colombiacompra.tienda.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {
	
	/**
	 * @serialField 
	 */
	private static final long serialVersionUID = -5220104233229391627L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idProducto;
	
	@Column(unique=true, name = "NOMBRE", nullable = false)
	private String nombre;
	
	@Column(name = "VALOR_UNITARIO", nullable = false)
	@Check(constraints = "VALOR_UNITARIO >= 0")
	private Long valorUnitario;
	
	@Column(name = "DISPONIBLE", length = 50, nullable = false)
	@Check(constraints = "DISPONIBLE >= 0")
	private Long disponible = 0L;
	
}
