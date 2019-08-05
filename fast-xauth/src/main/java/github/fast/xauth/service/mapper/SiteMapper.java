package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.SiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Site and its DTO SiteDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class, TemplateMapper.class})
public interface SiteMapper extends EntityMapper<SiteDTO, Site> {


    @Mapping(target = "workspaces", ignore = true)
    Site toEntity(SiteDTO siteDTO);

    default Site fromId(Long id) {
        if (id == null) {
            return null;
        }
        Site site = new Site();
        site.setId(id);
        return site;
    }
}
