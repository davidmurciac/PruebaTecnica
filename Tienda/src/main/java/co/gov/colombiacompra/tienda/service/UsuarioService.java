package co.gov.colombiacompra.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;
import co.gov.colombiacompra.tienda.domain.mapper.UsuarioMapper;
import co.gov.colombiacompra.tienda.repository.UsuarioRepository;

@Service
public class UsuarioService{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioMapper usuarioMapper;
	
	public Usuario obtenerUsuarioPorLogin(String login) {
		Usuario usuario;
		
		usuario = usuarioRepository.findByLogin(login);
		return usuario;
		
	}


	public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioMapper.DTOToEntity(usuarioDTO);

		usuarioRepository.saveAndFlush(usuario);
		
		return usuarioMapper.entityToDTO(usuario);
		
	};
	
	
}
