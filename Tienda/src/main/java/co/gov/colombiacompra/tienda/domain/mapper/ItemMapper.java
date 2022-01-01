package co.gov.colombiacompra.tienda.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.gov.colombiacompra.tienda.domain.Item;
import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.ItemDTO;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;


@Mapper(
        componentModel = "spring"
)
public interface ItemMapper extends MapStructMapper<Item, ItemDTO> {
	
	 @Override
	 @Mapping(source="producto", target = "producto")
	 ItemDTO entityToDTO(Item entity);

}
