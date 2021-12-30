package co.gov.colombiacompra.tienda.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.colombiacompra.tienda.domain.dto.CarritoDTO;
import co.gov.colombiacompra.tienda.domain.dto.ItemDTO;
import co.gov.colombiacompra.tienda.security.util.SecurityUtil;
import co.gov.colombiacompra.tienda.service.CarritoService;


/**
 * Controlador para acceder a los servicios relacionados con el carrito de Compras.
 * 
 * Permite la creación de un único carrito de compras activo por usuario
 * Permite la adición, eliminación o modificación de los items asociados a un carrito 
 * de compras.
 * Permite vacíar un carrito de compras
 * Permite deshabilitar un carrito de compras tras la venta exitosa.
 * 
 * 
 * @author David Murcia
 *
 */
@RestController
@RequestMapping("carrito")
public class CarritoController {

	
	/**
	 * Servicio de Carrito
	 */
	@Inject
	private CarritoService carritoService;
	
	/**
	 * Método que dado el token, obtiene el id del usuario autenticado
	 * 
	 * @param Authorization header de autorización del request que invoca
	 *        éste método
	 *        
	 * @return Identificador del usuario autenticado.
	 * 
	 * @author David.Murcia
	 */
	private Long ObtenerUid(String authorization) {
		String jwtToken = authorization.replace("Bearer ", "");

		Long uid = Long.parseLong(SecurityUtil.getTokenParam(jwtToken, "uid"));

		return uid;
	}
	
	/**
	 * Método PUT para vaciar el carro y actualizar el stock del producto
	 * 
	 * @param authorizationheader de autorización del request que invoca
	 *        éste método
	 * @return ResponseEntity con la información del Carrito actualizado
	 * 
	 * @author David.Murcia
	 */
	@PutMapping("vaciar-carro")
	public ResponseEntity<CarritoDTO> actualizar(@RequestHeader("Authorization") String authorization) {
		Long uid = ObtenerUid(authorization);
		CarritoDTO carritoDTO;
		try {
			carritoDTO = carritoService.vaciarCarrito(uid);
			return new ResponseEntity<CarritoDTO>(carritoDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<CarritoDTO>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método PUT para deshabilitar el carro tras la finalización de la compra
	 * 
	 * @param authorizationheader de autorización del request que invoca
	 *        éste método
	 * @return ResponseEntity con información de éxito o error.
	 * 
	 * @author David.Murcia
	 */
	@PutMapping("finalizar-compra")
	public ResponseEntity<String> deshabilitarCarrito(@RequestHeader("Authorization") String authorization) {
		Long uid = ObtenerUid(authorization);
		try {
			carritoService.deshabilitarCarrito(uid);

			return new ResponseEntity<>(null, null, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Método Post para la creación de ítems en el carro. 
	 * 
	 * @param authorization header de autorización del request que invoca
	 *        éste método
	 * @param itemDTO registro de producto a registrarle la cantidad
	 * @return ResponseEntity con la información del Carrito creado
	 * 
	 * @author David.Murcia
	 */
	@PostMapping("item/crear")
	public ResponseEntity<CarritoDTO> agregar(@RequestHeader("Authorization") String authorization,
			@RequestParam("producto") ItemDTO itemDTO) {
		Long uid = ObtenerUid(authorization);
		CarritoDTO carritoDTO;
		try {
			carritoDTO = carritoService.agregarProducto(uid, itemDTO);
			return new ResponseEntity<CarritoDTO>(carritoDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<CarritoDTO>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	/**
	 * Método PUT para la actualización de los ítems en el carro 
	 * 
	 * @param authorization header de autorización del request que invoca
	 *        éste método
	 * @param itemDTO registro de producto a actualizarle la cantidad
	 * @return ResponseEntity con la información del Carrito actualizado
	 * 
	 * @author David.Murcia
	 */
	@PutMapping("item/actualizar")
	public ResponseEntity<CarritoDTO> actualizar(@RequestHeader("Authorization") String authorization,
			@RequestParam("producto") ItemDTO itemDTO) {
		Long uid = ObtenerUid(authorization);
		CarritoDTO carritoDTO;
		try {
			carritoDTO = carritoService.actualizarProducto(uid, itemDTO);
			return new ResponseEntity<CarritoDTO>(carritoDTO, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<CarritoDTO>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Método de eliminación del registro de producto en el carro.
	 * 
	 * @param authorization header de autorización del request que invoca
	 *        éste método
	 * @param itemDTO registro de producto a eliminar
	 * @return ResponseEntity con la información del Carrito actualizado
	 * 
	 * @author David.Murcia
	 */
	@DeleteMapping("item/eliminar")
	public ResponseEntity<CarritoDTO> eliminar(@RequestHeader("Authorization") String authorization,
			@RequestParam("producto") ItemDTO itemDTO) {
		Long uid = ObtenerUid(authorization);
		CarritoDTO carritoDTO = carritoService.eliminarProducto(uid, itemDTO);

		return new ResponseEntity<CarritoDTO>(carritoDTO, HttpStatus.OK);
	}
	
	/**
	 * Método que lista los productos asociados a un carrito
	 * 
	 * @param authorization header de autorización del request que invoca
	 *        éste método
	 * @return ResponseEntity con la información de los items (Producto y cantidad)
	 *         asociados al Carrito
	 * 
	 * @throws Exception
	 * 
	 * @author David.Murcia
	 */
	@GetMapping("listar_productos")
	public ResponseEntity<List<ItemDTO>> listarProductos(@RequestHeader("Authorization") String authorization)
			throws Exception {

		Long uid = ObtenerUid(authorization);
		List<ItemDTO> itemsDTO = carritoService.listarProductos(uid);

		return new ResponseEntity<List<ItemDTO>>(itemsDTO, HttpStatus.OK);
	}

}