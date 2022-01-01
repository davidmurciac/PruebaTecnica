package co.gov.colombiacompra.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;
import co.gov.colombiacompra.tienda.domain.mapper.UsuarioMapper;
import co.gov.colombiacompra.tienda.repository.UsuarioRepository;


/**
 * Servicio de usuarios
 * 
 * @author David Murcia
 *
 */
@Service
public class UsuarioService{
	
	/**
	 * Repositorio de usuario
	 */
	@Autowired
	UsuarioRepository usuarioRepository;
	
	/**
	 * Mapper de usuarios
	 */
	@Autowired
	UsuarioMapper usuarioMapper;
	
	
	/**
	 * Consulta de usuarios basado en su cuenta de acceso,
	 * 
	 * @param login cuenta de acceso única de usuario
	 * 
	 * @return Información de usuario.
	 */
	public Usuario obtenerUsuarioPorLogin(String login) {
		Usuario usuario;
		
		usuario = usuarioRepository.findByLogin(login);
		return usuario;
		
	}

	/**
	 * Método para creación de usuarios
	 * 
	 * @param usuarioDTO Usuario que se quiere crear
	 * @throws Exception En caso de presentarse algún inconveniente al crear el usuario
	 * 
	 * @author David.Murcia
	 */
	public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioMapper.DTOToEntity(usuarioDTO);

		usuarioRepository.saveAndFlush(usuario);
		
		return usuarioMapper.entityToDTO(usuario);
		
	}

	
	/**
	 * Método para actualización de usuarios
	 * 
	 * @param usuarioDTO Usuario que se quiere actualizar
	 * @throws Exception En caso de presentarse algún inconveniente al actualizar
	 *                   el usuario.
	 *  
	 *  @author David.Murcia
	 */
	public UsuarioDTO actualizar(UsuarioDTO usuarioDTO) throws Exception {
		Usuario usuarioDespues = usuarioMapper.DTOToEntity(usuarioDTO);
		
		Usuario usuarioAntes = usuarioRepository.findByIdUsuario(usuarioDTO.getIdUsuario());
		
		if (!usuarioAntes.getLogin().equals(usuarioDespues.getLogin())) {
			throw new Exception("Error: No se puede cambiar el nombre de la cuenta.");
		}
		
		if (usuarioDespues.getPassword() == null || usuarioDespues.getPassword().equals(""))
		{
			usuarioDespues.setPassword(usuarioAntes.getPassword());
		}
		
		usuarioRepository.saveAndFlush(usuarioDespues);
		
		
		return usuarioMapper.entityToDTO(usuarioDespues);
	};
	
	/**
	 * Método para eliminación de usuarios
	 * 
	 * @param usuarioDTO Usuario que se quiere eliminar
	 * @throws Exception En caso de presentarse algún inconveniente al eliminar el usuario
	 * 
	 * @author David.Murcia
	 * 
	 */
	public void eliminar(UsuarioDTO usuarioDTO) throws Exception {
			
		Usuario usuario = usuarioRepository.findByIdUsuario(usuarioDTO.getIdUsuario());
		
		usuarioRepository.delete(usuario);
		
		  
	};
	
	
}
