package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.MenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Menu and its DTO MenuDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, TemplateMapper.class, AuthMapper.class})
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    @Mapping(source = "parent.id", target = "parentId")
    MenuDTO toDto(Menu menu);

    @Mapping(source = "parentId", target = "parent")
    Menu toEntity(MenuDTO menuDTO);

    default Menu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(id);
        return menu;
    }
}
