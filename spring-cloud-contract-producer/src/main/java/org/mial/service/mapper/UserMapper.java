package org.mial.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mial.dto.UserDTO;
import org.mial.model.User;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", source = "entity.id"),
        @Mapping(target = "firstName", source = "entity.firstName"),
        @Mapping(target = "lastName", source = "entity.lastName"),
        @Mapping(target = "email", source = "entity.email")
    })
    UserDTO map(User entity);

    @Mappings({
        @Mapping(target = "id", source = "dto.id"),
        @Mapping(target = "firstName", source = "dto.firstName"),
        @Mapping(target = "lastName", source = "dto.lastName"),
        @Mapping(target = "email", source = "dto.email")
    })
    User map(UserDTO dto);
}
