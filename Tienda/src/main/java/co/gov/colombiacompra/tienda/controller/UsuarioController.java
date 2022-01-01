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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;
import co.gov.colombiacompra.tienda.domain.enumeration.Rol;
import co.gov.colombiacompra.tienda.domain.mapper.UsuarioMapper;
import co.gov.colombiacompra.tienda.security.util.SecurityUtil;
import co.gov.colombiacompra.tienda.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Controlador para acceder a los servicios relacionados con el Usuario de un
 * Maketplace.
 * 
 * Permite la consulta de un usuario
 * Permite la autenticación de un usuario y la creación de un Token
 * Permite la creación de un usuario
 * Permite la actualización de un usuario
 * Permite el borrado de un usuario
 *
 */
@RestController
@RequestMapping(value = "usuario")
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
	public ResponseEntity<UsuarioDTO> login(@RequestParam("usuario") String login,
			@RequestParam("clave") String password) throws Exception {

		Usuario usuario = usuarioService.obtenerUsuarioPorLogin(login);
		UsuarioDTO usuarioDTO = usuarioMapper.entityToDTO(usuario);

		if (usuario == null) {
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(), HttpStatus.FORBIDDEN);
		}

		if (!usuario.getPassword().equals(password)) {
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(), HttpStatus.FORBIDDEN);
		}

		String token = getJWTToken(login, usuario.getIdUsuario(), usuario.getRol().toString());

		usuarioDTO.setToken(token);
		return new ResponseEntity<UsuarioDTO>(usuarioDTO, HttpStatus.OK);

	}

	/**
	 * Método adaptado del proporcionado por JWT para la generación de un token
	 * 
	 * @param username cuenta de usuario
	 * @param uid      identificador del usuario
	 * @param rol      rol asignado al usuario
	 * @return
	 */
	private String getJWTToken(@RequestBody String username, Long uid, String rol) {

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(rol);

		String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("uid", uid).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	/**
	 * Método Post para la creación de un usuario.
	 * 
	 * @param UsuarioDTO Información del usuario que se desea crear
	 * @return ResponseEntity con la información del Usuario creado
	 * 
	 * @author David.Murcia
	 */
	@PostMapping
	public ResponseEntity<UsuarioDTO> agregar(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			usuarioDTO = usuarioService.crearUsuario(usuarioDTO);

			return new ResponseEntity<UsuarioDTO>(usuarioDTO, HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<UsuarioDTO>(usuarioDTO, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método Post para la modificación de un usuario.
	 * 
	 * @param authorization header de autorización del request que invoca éste
	 *                      método
	 * @param UsuarioDTO    Información de usuario que se desea actualizar
	 * @return ResponseEntity con la información del Usuario actualizado
	 * 
	 * @author David.Murcia
	 */
	@PutMapping
	public ResponseEntity<UsuarioDTO> actualizar(@RequestHeader("Authorization") String authorization,
			@RequestBody UsuarioDTO usuarioDTO) {
		try {
			Long uid = SecurityUtil.ObtenerUid(authorization);
			List authorities = SecurityUtil.getAuthorities(authorization);

			if (uid.equals(usuarioDTO.getIdUsuario()) || authorities.contains(Rol.ADMINISTRADOR.toString())) {
				usuarioDTO = usuarioService.actualizar(usuarioDTO);
				return new ResponseEntity<UsuarioDTO>(usuarioDTO, HttpStatus.OK);
			} else {
				throw new Exception("Error: Sólo puede realizarse esta acción por el propietario de la cuenta ."
						+ " o por un administrador");
			}

		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("error", e.getMessage());
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Método Delete para la eliminación de un usuario.
	 * 
	 * @param authorization header de autorización del request que invoca éste
	 *                      método
	 * @param UsuarioDTO    Información de usuario que se desea eliminar
	 * @return ResponseEntity con la información de eliminación del usuario.
	 * 
	 * @author David.Murcia
	 */
	@DeleteMapping
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<String> eliminar(@RequestHeader("Authorization") String authorization,
			@RequestBody UsuarioDTO usuarioDTO) {
		try {
			List authorities = SecurityUtil.getAuthorities(authorization);
			
			System.out.println(authorities.get(0));
			
			if (authorities.contains(Rol.ADMINISTRADOR.toString())) {
				usuarioService.eliminar(usuarioDTO);
				return new ResponseEntity<String>(usuarioDTO.getLogin() + " eliminado", HttpStatus.OK);
			} else {
				throw new Exception("Error: Sólo puede realizarse esta acción por un administrador");
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}