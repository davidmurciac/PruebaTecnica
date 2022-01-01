package co.gov.colombiacompra.tienda.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import com.fasterxml.jackson.annotation.JsonBackReference;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ITEM_CARRITO")
public class Item implements Serializable {
	
	/**
	 * @serialField 
	 */
	private static final long serialVersionUID = -1908564599200499262L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idItem;
	
	@ManyToOne
	@JoinColumn(name = "ID_CARRITO")
	@JsonBackReference
	private Carrito carrito;
		
	@ManyToOne
	@JoinColumn(name = "ID_PRODUCTO")
	private Producto producto;
	
	@Column(name = "CANTIDAD", nullable = false)
	@Check(constraints = "CANTIDAD > 0")
	private Long cantidad;
	
}
