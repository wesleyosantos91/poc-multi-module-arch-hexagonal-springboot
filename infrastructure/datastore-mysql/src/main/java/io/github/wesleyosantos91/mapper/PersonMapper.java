package io.github.wesleyosantos91.mapper;

import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.entity.PersonEntity;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonDomain toDomain(PersonEntity entity);
    PersonEntity toEntity(PersonDomain domain);


    default List<PersonDomain> toListDomain(List<PersonEntity> entities){
        List<PersonDomain> list = new ArrayList<>();
        entities.forEach(d-> list.add(toDomain(d)));
        return list;
    }
}
