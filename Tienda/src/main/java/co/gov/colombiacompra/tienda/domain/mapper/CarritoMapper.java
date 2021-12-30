package co.gov.colombiacompra.tienda.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.gov.colombiacompra.tienda.domain.Carrito;
import co.gov.colombiacompra.tienda.domain.dto.CarritoDTO;


@Mapper(
        componentModel = "spring"
)
public interface CarritoMapper extends MapStructMapper<Carrito, CarritoDTO> {
	
	
	@Mapping(source = "usuario", target = "usuario")
	@Mapping(source = "items", target="items")
	CarritoDTO entityToDTO(Carrito entity);
}
