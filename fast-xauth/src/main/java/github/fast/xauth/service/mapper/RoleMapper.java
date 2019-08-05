package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.RoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Role and its DTO RoleDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class})
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

    @Mapping(source = "workspace.id", target = "workspaceId")
    RoleDTO toDto(Role role);

    @Mapping(source = "workspaceId", target = "workspace")
    @Mapping(target = "sites", ignore = true)
    @Mapping(target = "auths", ignore = true)
    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    default Role fromId(Long id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}
