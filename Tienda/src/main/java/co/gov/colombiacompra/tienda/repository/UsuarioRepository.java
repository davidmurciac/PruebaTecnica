package co.gov.colombiacompra.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.enumeration.TipoIdentificacion;

@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	/**
	 * Méteodo que busca un usuario dado un id
	 * 
	 * @param idUsuario identificador único asociado a un usuario
	 * 
	 * @return Entidad Usuario obtenida
	 * 
	 * @author David.Murcia
	 */
	public Usuario findByIdUsuario(Long idUsuario);
	
	/**
	 * Méteodo que busca un producto dado su login
	 * 
	 * @param login cuenta de usuario única
	 * 
	 * @return Entidad Usuario obtenida
	 * 
	 * @author David.Murcia
	 */
	public Usuario findByLogin(String login);
		
	
}
