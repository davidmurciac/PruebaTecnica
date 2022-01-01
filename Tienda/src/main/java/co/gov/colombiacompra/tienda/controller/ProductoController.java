package co.gov.colombiacompra.tienda.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.colombiacompra.tienda.domain.dto.ProductoDTO;
import co.gov.colombiacompra.tienda.domain.enumeration.Rol;
import co.gov.colombiacompra.tienda.security.util.SecurityUtil;
import co.gov.colombiacompra.tienda.service.ProductoService;

/**
 * Controlador para acceder a los servicios relacionados con el Producto de un
 * Maketplace.
 * 
 * Permite la creacion de un producto Permite la actualizacion de un producto
 * Permite el borrado de un producto Permite la consulta de un producto
 * 
 *
 */
@RestController
@RequestMapping(value = "producto")
public class ProductoController {

	/**
	 * Secret Key de validación o generación del TOKEN
	 */
	@Value("${jwt.secret}")
	private String secretKey;

	/**
	 * Servicios de producto
	 */
	@Inject
	private ProductoService productoService;

	/**
	 * Método Post para la creación de un producto.
	 * 
	 * @param authorization header de autorización del request que invoca éste
	 *                      método
	 * @param ProductoDTO   Información del producto que se desea crear
	 * @return ResponseEntity con la información del Producto creado
	 * 
	 * @author David.Murcia
	 */
	@PostMapping
	public ResponseEntity<ProductoDTO> agregar(@RequestHeader("Authorization") String authorization,
			@RequestBody ProductoDTO productoDTO) {
		try {

			List authorities = SecurityUtil.getAuthorities(authorization);

			if (authorities.contains(Rol.ADMINISTRADOR.toString())) {
				productoDTO = productoService.crearProducto(productoDTO);
				return new ResponseEntity<ProductoDTO>(productoDTO, HttpStatus.OK);
			} else {
				throw new Exception("Error: Sólo puede realizarse esta acción por un administrador");
			}

		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<ProductoDTO>(productoDTO, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método Post para la modificación de un producto.
	 * 
	 * @param authorization header de autorización del request que invoca éste
	 *                      método
	 * @param ProductoDTO   Información del producto que se desea actualizar
	 * @return ResponseEntity con la información del Producto actualizado
	 * 
	 * @author David.Murcia
	 */
	@PutMapping
	public ResponseEntity<ProductoDTO> actualizar(@RequestHeader("Authorization") String authorization,
			@RequestBody ProductoDTO productoDTO) {
		try {
			List authorities = SecurityUtil.getAuthorities(authorization);

			if (authorities.contains(Rol.ADMINISTRADOR.toString())) {
				productoDTO = productoService.actualizar(productoDTO);
				return new ResponseEntity<ProductoDTO>(productoDTO, HttpStatus.OK);
			} else {
				throw new Exception("Error: Sólo puede realizarse esta acción por un administrador");
			}

		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<ProductoDTO>(new ProductoDTO(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método Delete para la eliminación de un producto.
	 * 
	 * @param authorization header de autorización del request que invoca éste
	 *                      método
	 * @param ProductoDTO   Información del producto que se desea eliminar
	 * @return ResponseEntity con la información de la acción de eliminación
	 * 
	 * @author David.Murcia
	 */
	@DeleteMapping
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<String> eliminar(@RequestHeader("Authorization") String authorization,
			@RequestBody ProductoDTO productoDTO) {
		try {
			List authorities = SecurityUtil.getAuthorities(authorization);

			System.out.println(authorities.get(0));

			if (authorities.contains(Rol.ADMINISTRADOR.toString())) {
				productoService.eliminar(productoDTO);
				return new ResponseEntity<String>(productoDTO.getNombre() + " eliminado", HttpStatus.OK);
			} else {
				throw new Exception("Error: Sólo puede realizarse esta acción por un administrador");
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * Método Get para la consulta de todos los productos.
	 * 
	 * @return ResponseEntity con la información de todos los productos
	 * 
	 * @author David.Murcia
	 */
	@GetMapping
	public ResponseEntity<List<ProductoDTO>> agregar() {
		List productos = productoService.listarProductos();
		return new ResponseEntity<List<ProductoDTO>>(productos, HttpStatus.OK);


	}

	
	

}