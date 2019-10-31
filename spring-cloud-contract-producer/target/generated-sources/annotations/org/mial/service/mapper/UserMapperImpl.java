package org.mial.service.mapper;

import javax.annotation.Generated;
import org.mial.dto.UserDTO;
import org.mial.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-31T15:11:20+0100",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.4 (JetBrains s.r.o)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO map(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName( entity.getFirstName() );
        userDTO.setLastName( entity.getLastName() );
        userDTO.setId( entity.getId() );
        userDTO.setEmail( entity.getEmail() );

        return userDTO;
    }

    @Override
    public User map(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setId( dto.getId() );
        user.setEmail( dto.getEmail() );

        return user;
    }
}
