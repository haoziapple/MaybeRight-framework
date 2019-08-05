package github.fast.xauth.service.mapper;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.WorkspaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Workspace and its DTO WorkspaceDTO.
 */
@Mapper(componentModel = "spring", uses = {SiteMapper.class, TemplateMapper.class})
public interface WorkspaceMapper extends EntityMapper<WorkspaceDTO, Workspace> {

    @Mapping(source = "parent.id", target = "parentId")
    WorkspaceDTO toDto(Workspace workspace);

    @Mapping(source = "parentId", target = "parent")
    Workspace toEntity(WorkspaceDTO workspaceDTO);

    default Workspace fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setId(id);
        return workspace;
    }
}
