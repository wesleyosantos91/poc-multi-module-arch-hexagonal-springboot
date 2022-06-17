package io.github.wesleyosantos91.mapper;

import io.github.wesleyosantos91.core.domain.PersonDomain;
import io.github.wesleyosantos91.entity.PersonEntity;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    default Page<PersonDomain> toPageResponse(Page<PersonEntity> pages){
        List<PersonDomain> list = toListDomain(pages.getContent());
        return new PageImpl<>(list, pages.getPageable(), pages.getTotalElements());

    }
}
