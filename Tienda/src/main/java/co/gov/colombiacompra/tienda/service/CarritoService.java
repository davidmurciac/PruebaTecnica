package co.gov.colombiacompra.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.colombiacompra.tienda.domain.Carrito;
import co.gov.colombiacompra.tienda.domain.Item;
import co.gov.colombiacompra.tienda.domain.Producto;
import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.CarritoDTO;
import co.gov.colombiacompra.tienda.domain.dto.ItemDTO;
import co.gov.colombiacompra.tienda.domain.mapper.CarritoMapper;
import co.gov.colombiacompra.tienda.domain.mapper.ItemMapper;
import co.gov.colombiacompra.tienda.repository.CarritoRepository;
import co.gov.colombiacompra.tienda.repository.ItemRepository;
import co.gov.colombiacompra.tienda.repository.ProductoRepository;
import co.gov.colombiacompra.tienda.repository.UsuarioRepository;

@Service
public class CarritoService {
		
	/**
	 * Repositorio de Carrito
	 */
	@Autowired
	CarritoRepository carritoRepository;
	
	/**
	 * Repositorio de datos de Item
	 */
	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * Repositorio de datos de producto
	 */
	@Autowired
	ProductoRepository productoRepository;
	
	/**
	 * Repositorio de datos de usuario
	 */
	@Autowired
	UsuarioRepository usuarioRepository;
	
	/**
	 * Mapeador de Ítems
	 */
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * Mapeador de Carritos
	 */
	@Autowired
	private CarritoMapper carritoMapper;
	
	/**
	 * Procedimiento que basado en el id del usuario autenticado obtiene
	 * el carro de compras asociado, de no tener ninguno activo, procede
	 * a crearlo.
	 * 
	 * @param usuarioId
	 * @return Entidad Carrito que se asocia una sola vez al 
	 *         tiempo por usuario
	 */
	public Carrito obtenerCarrito(Long usuarioId) {
		Usuario usuario = usuarioRepository.findByUsuarioId(usuarioId);
		
		Carrito carrito = carritoRepository.findByUsuarioAndActivo(usuario,true);
				
		if(carrito == null) {
			
			carrito = new Carrito();
			carrito.setUsuario(usuario);
			carrito.setActivo(true);
			carrito = carritoRepository.saveAndFlush(carrito);
		}
		
		return carrito;
	}
	
	
	/**
	 * Método de eliminación del contenido del carrito. 
	 * 
	 * Se encarga de vaciar los elementos del carro y restaurar los productos
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * 
	 * @author David.Murcia
	 */
	public CarritoDTO vaciarCarrito(Long uid) {
		Carrito carrito = obtenerCarrito(uid);
		
		carrito.getItems().
		        forEach(
		        		item -> {
		        			ItemDTO itemDTO= itemMapper.entityToDTO(item);
		        			eliminarProducto(uid,itemDTO);
		        		});		
		
		CarritoDTO carritoDTO = carritoMapper.entityToDTO(carrito);
		return carritoDTO;
		
	}
	
	/**
	 * Método que deshabilita el carrito cuando se ha realizado la compra, dejando
	 * los ítems asociado porque fueron vendidos.
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * 
	 * @author David.Murcia
	 */
	public void deshabilitarCarrito(Long uid) {
		Carrito carrito = obtenerCarrito(uid);
		carrito.setActivo(false);
		
		carritoRepository.saveAndFlush(carrito);		
	}
		
	/**
	 * Lista los productos asociados a un carrito de compras
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * @return Lista de ítems asociados al carrito de compras.
	 * 
	 * @author David Murcia
	 */
	public List<ItemDTO> listarProductos(Long uid) {
		
		Carrito carrito = this.obtenerCarrito(uid);
		List<Item> items = carrito.getItems();
		List<ItemDTO> itemsDTO = itemMapper.entityToDTO(items);
		return itemsDTO;
	}
	
	
	/**
	 * Método de adición de productos. 
	 * 
	 * Se encarga de agregar la cantidad completa de productos que indica
	 * el parámetro ItemDTO
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * @param itemDTO Información del Producto que se agrega y en qué cantidad.
	 * @return Información completa del carrito al que se le agregó el producto.
	 * 
	 * @author David.Murcia
	 * @throws Exception 
	 */
	public CarritoDTO agregarProducto(Long uid, ItemDTO itemDTO) throws Exception {
		Carrito carrito = obtenerCarrito(uid);
		
		if(itemDTO.getIdItem() != null) {
			throw new Exception("Error: Se solicitó crear producto existente en el carro.");
		}
		
		Producto producto = productoRepository.findByIdProducto(itemDTO.getProducto().getIdProducto());
		
		if(producto.getDisponible().compareTo( itemDTO.getCantidad()) >= 0) {
			producto.setDisponible(producto.getDisponible() - itemDTO.getCantidad());
			Item item = new Item();
			item.setCarrito(carrito);
			item.setProducto(producto);
			item.setCantidad(itemDTO.getCantidad());
			
			itemRepository.saveAndFlush(item);
			productoRepository.saveAndFlush(producto);
		}
		else {
			throw new Exception("Error: Stock del producto no disponible.");
		}
				
		CarritoDTO carritoDTO = carritoMapper.entityToDTO(carrito);
		return carritoDTO;
			
	}
	
	/**
	 * Método de modificación de productos que ya se han agregado al carrito de
	 * compras. 
	 * 
	 * Se encarga de modificar la cantidad de productos que indica el 
	 * parámetro itemDTO
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * @param itemDTO Información del Producto que se actualizará y en qué cantidad.
	 * @return Información completa del carrito al que se le agregó el producto.
	 * 
	 * @author David.Murcia
	 * @throws Exception 
	 */
	public CarritoDTO actualizarProducto(Long uid, ItemDTO itemDTO) throws Exception {
		
		
		if(itemDTO.getIdItem() == null) {
			throw new Exception("Error: Se solicitó actualizar un item que no parece estar" +
		                        " registrado.");
		}
		
		Producto producto = productoRepository.findByIdProducto(itemDTO.getProducto().getIdProducto());
		
		Item item = itemRepository.findByIdItem(itemDTO.getIdItem());
		Long diferencia = producto.getDisponible() - itemDTO.getCantidad() + item.getCantidad();
		
		if(diferencia >= 0) {
			producto.setDisponible(diferencia);
			item.setCantidad(itemDTO.getCantidad());
			
			itemRepository.saveAndFlush(item);
			productoRepository.saveAndFlush(producto);
		}
		else {
			throw new Exception("Error: Stock del producto no disponible para éste cambio.");
		}
		
		
		Carrito carrito = obtenerCarrito(uid);
				
		CarritoDTO carritoDTO = carritoMapper.entityToDTO(carrito);
		return carritoDTO;
			
	}
	
	/**
	 * Método de eliminación total de productos. 
	 * 
	 * Se encarga de modificar la cantidad de productos que indica el 
	 * parámetro itemDTO
	 * 
	 * @param uid Identificador de usuario que se vincula al carrito de compras.
	 * @param itemDTO Información del Producto que se actualizará y en qué cantidad.
	 * @return Información completa del carrito al que se le agregó el producto.
	 * 
	 * @author David.Murcia
	 */
	public CarritoDTO eliminarProducto(Long uid, ItemDTO itemDTO) {
		
		Item item = itemRepository.findByIdItem(itemDTO.getIdItem());
		
		Producto producto = productoRepository.getById(uid);
		
		producto.setDisponible(producto.getDisponible() + item.getCantidad());
		
		productoRepository.saveAndFlush(producto);
		itemRepository.delete(item);
		
		Carrito carrito = obtenerCarrito(uid);		
		CarritoDTO carritoDTO = carritoMapper.entityToDTO(carrito);
		return carritoDTO;
	}
	
		
}
