package co.gov.colombiacompra.tienda.domain.mapper;

import java.util.List;

public interface MapStructMapper<E,D> {
    D entityToDTO(E entity);
    E DTOToEntity(D dto);
    
    List<D> entityToDTO(List<E> entity);
    List<E> DTOToEntity(List<D> dto);
}