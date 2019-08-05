package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.AuthDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Auth and its DTO AuthDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, TemplateMapper.class})
public interface AuthMapper extends EntityMapper<AuthDTO, Auth> {


    @Mapping(target = "menus", ignore = true)
    Auth toEntity(AuthDTO authDTO);

    default Auth fromId(Long id) {
        if (id == null) {
            return null;
        }
        Auth auth = new Auth();
        auth.setId(id);
        return auth;
    }
}
