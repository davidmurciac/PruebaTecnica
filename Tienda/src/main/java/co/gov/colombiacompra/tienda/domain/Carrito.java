package co.gov.colombiacompra.tienda.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "CARRITO")
public class Carrito implements Serializable{
	
	/**
	 * @serialField 
	 */
	private static final long serialVersionUID = 4369841868614943183L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCarrito;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.LAZY, 
			   mappedBy = "carrito", 
			   cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Item> items;
	
	@Column(name = "ACTIVO")
	private Boolean activo;
		
}
