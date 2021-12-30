package co.gov.colombiacompra.tienda.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.gov.colombiacompra.tienda.domain.Producto;
import co.gov.colombiacompra.tienda.domain.Usuario;
import co.gov.colombiacompra.tienda.domain.dto.ProductoDTO;
import co.gov.colombiacompra.tienda.domain.dto.UsuarioDTO;


@Mapper(
        componentModel = "spring"
)
public interface ProductoMapper extends MapStructMapper<Producto, ProductoDTO> {
	
}
