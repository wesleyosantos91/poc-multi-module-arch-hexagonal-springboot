package io.github.wesleyosantos91.api.v1.mapper;

import io.github.wesleyosantos91.api.v1.request.PersonRequest;
import io.github.wesleyosantos91.api.v1.response.PersonResponse;
import io.github.wesleyosantos91.core.domain.PersonDomain;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonHttpMapper {

    PersonHttpMapper INSTANCE = Mappers.getMapper(PersonHttpMapper.class);
    PersonDomain toDomain(PersonRequest request);
    PersonRequest toRequest(PersonDomain domain);

    PersonDomain toDomain(PersonResponse response);
    PersonResponse toResponse(PersonDomain domain);


    default List<PersonResponse> toListResponse(List<PersonDomain> domains){
        List<PersonResponse> list = new ArrayList<>();
        domains.forEach(d-> list.add(toResponse(d)));
        return list;
    }
}
