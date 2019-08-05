package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {


    @Mapping(target = "workspaces", ignore = true)
    @Mapping(target = "sites", ignore = true)
    @Mapping(target = "auths", ignore = true)
    @Mapping(target = "menus", ignore = true)
    Template toEntity(TemplateDTO templateDTO);

    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
