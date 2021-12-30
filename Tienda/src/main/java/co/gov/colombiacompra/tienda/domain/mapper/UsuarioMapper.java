package co.gov.colombiacompra.tienda.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;


@Mapper(
        componentModel = "spring"
)
public interface UsuarioMapper extends MapStructMapper<Usuario, UsuarioDTO> {
	
	 @Override
	 @Mapping(target = "password", ignore = true )
	 UsuarioDTO entityToDTO(Usuario entity);

}
