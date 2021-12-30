package co.gov.colombiacompra.tienda.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.CarritoDTO;
import co.gov.colombiacompra.tienda.domain.dto.ItemDTO;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;
import co.gov.colombiacompra.tienda.domain.mapper.UsuarioMapper;
import co.gov.colombiacompra.tienda.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



/**
 * Controlador para acceder a los servicios relacionados con el Usuario de un Maketplace.
 * 
 * Permite la autenticación de un usuario y la creación de un Token 
 * 
 *
 */
@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	/**
	 * Secret Key de validación o generación del TOKEN
	 */
	@Value("${jwt.secret}")
	private String secretKey;
	
	
	/**
	 * Servicios de usuario
	 */
	@Inject
    private UsuarioService usuarioService;
	
	
	/**
	 * Mapeo de usuarios
	 */
	@Autowired
	private UsuarioMapper usuarioMapper;
	
	 
	/**
	 * Métod post que permite a un usuario autenticarse
	 * 
	 * @param login
	 * @param password
	 * @return Información del usuario autenticado con su respectivo Token
	 * @throws Exception
	 * 
	 * @author David.Murcia
	 */
	@PostMapping("login")
	public UsuarioDTO login(@RequestParam("usuario") String login, @RequestParam("clave") String password) throws Exception {
		
		Usuario usuario = usuarioService.obtenerUsuarioPorLogin(login);
		UsuarioDTO usuarioDTO = usuarioMapper.entityToDTO(usuario);
		
		if (usuario == null) {
			throw new Exception("Usuario Inexistente");
		}
		
		if (!usuario.getPassword().equals(password)) {
			throw new Exception("Credenciales Incorrectas");
		}
		
		String token = getJWTToken(login,usuario.getIdUsuario(),usuario.getRol().toString());
		
		usuarioDTO.setToken(token);		
		return usuarioDTO;
		
	}
	
	
	/**
	 * Método adaptado del proporcionado por JWT para la generación de un token
	 * 
	 * @param username cuenta de usuario 
	 * @param uid identificador del usuario
	 * @param rol rol asignado al usuario
	 * @return
	 */
	private String getJWTToken(String username, Long uid, String rol) {
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(rol);
		
		String token = Jwts
				.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList())).claim("uid", uid)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
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
	@PostMapping("crear")
	public ResponseEntity<UsuarioDTO> agregar(@RequestParam("usuario") UsuarioDTO usuarioDTO) {
		
		try {
			usuarioDTO = usuarioService.crearUsuario(usuarioDTO);
			return new ResponseEntity<UsuarioDTO>(usuarioDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<UsuarioDTO>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
}