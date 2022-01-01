package co.gov.colombiacompra.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.colombiacompra.tienda.domain.Producto;
import co.gov.colombiacompra.tienda.domain.dto.ProductoDTO;
import co.gov.colombiacompra.tienda.domain.mapper.ProductoMapper;
import co.gov.colombiacompra.tienda.repository.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired
	ProductoRepository productoRepository;
	
	/**
	 * Mapeo de productos
	 */
	@Autowired
	private ProductoMapper productoMapper;
	
	/**
	 * Método para creación de productos
	 * 
	 * @param productoDTO Producto que se quiere crear
	 * @throws Exception En caso de presentarse algún inconveniente al crear el producto
	 * 
	 * @author David.Murcia
	 */
	public ProductoDTO crearProducto(ProductoDTO productoDTO) {
		Producto producto = productoMapper.DTOToEntity(productoDTO);

		productoRepository.saveAndFlush(producto);
		
		return productoMapper.entityToDTO(producto);
		
	}

	
	/**
	 * Método para actualización de productos
	 * 
	 * @param productoDTO Producto que se quiere actualizar
	 * @throws Exception En caso de presentarse algún inconveniente al actualizar
	 *                   el producto.
	 *  
	 *  @author David.Murcia
	 */
	public ProductoDTO actualizar(ProductoDTO productoDTO) throws Exception {
		Producto productoDespues = productoMapper.DTOToEntity(productoDTO);
		
		productoRepository.saveAndFlush(productoDespues);
				
		return productoMapper.entityToDTO(productoDespues);
	};
	
	/**
	 * Método para eliminación de productos
	 * 
	 * @param productoDTO Producto que se quiere eliminar
	 * @throws Exception En caso de presentarse algún inconveniente al eliminar el producto
	 * 
	 * @author David.Murcia
	 * 
	 */
	public void eliminar(ProductoDTO productoDTO) throws Exception {
			
		Producto producto = productoRepository.findByIdProducto(productoDTO.getIdProducto());
		
		productoRepository.delete(producto);
		
		  
	}
	

	/**
	 * Método para listar todos los productos 
	 * 
	 * @return Lista de productos 
	 * @author David.Murcia
	 * 
	 */
	public List listarProductos() {
		List<Producto> productos = productoRepository.findAll();
				
		return productoMapper.entityToDTO(productos);
	};
	
}
