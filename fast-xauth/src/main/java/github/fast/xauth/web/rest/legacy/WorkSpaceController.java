package github.fast.xauth.web.rest.legacy;

import github.fast.xauth.domain.enumeration.RoleType;
import github.fast.xauth.service.ProfileService;
import github.fast.xauth.service.RoleService;
import github.fast.xauth.service.WorkspaceQueryService;
import github.fast.xauth.service.WorkspaceService;
import github.fast.xauth.service.dto.ProfileDTO;
import github.fast.xauth.service.dto.RoleDTO;
import github.fast.xauth.service.dto.WorkspaceDTO;
import github.fast.xauth.web.rest.errors.BadRequestAlertException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static github.fast.xauth.web.rest.legacy.LegacyConstants.LEGACY_PATH;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-06 10:32
 */
@RestController
@RequestMapping(LEGACY_PATH + "/workspace")
public class WorkSpaceController extends BasicController {
    private final Logger log = LoggerFactory.getLogger(WorkSpaceController.class);

    private final WorkspaceService workspaceService;

    private final WorkspaceQueryService workspaceQueryService;

    private final RoleService roleService;

    private final ProfileService profileService;

    public WorkSpaceController(WorkspaceService workspaceService,
                               WorkspaceQueryService workspaceQueryService,
                               RoleService roleService,
                               ProfileService profileService) {
        this.workspaceService = workspaceService;
        this.workspaceQueryService = workspaceQueryService;
        this.roleService = roleService;
        this.profileService = profileService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有工作空间")
    public ActionResult list() {
        return new ActionResult(workspaceQueryService.findByCriteria(null));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加工作空间")
    public ActionResult add(@RequestBody WorkspaceDTO workspaceDTO) {
        log.debug("REST request to add Workspace : {}", workspaceDTO);
        if (workspaceDTO.getId() != null) {
            throw new BadRequestAlertException("A new workspace cannot already have an ID", "xauthWorkspace", "idexists");
        }

        // TODO 初始化workspace时需要指定site与权限模板
        WorkspaceDTO resultWorkspace = workspaceService.save(workspaceDTO);
        RoleDTO roleDTO = this.initAdminRole(resultWorkspace);
        ProfileDTO profileDTO = this.initAdminUser(resultWorkspace, roleDTO);

        return new ActionResult(profileDTO);
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ApiOperation(value = "查看工作空间信息")
    public ActionResult item(@RequestParam("id") String id) {
        // TODO 为空时返回错误码
        return new ActionResult(workspaceService.findOne(Long.valueOf(id)).orElse(null));
    }

    /**
     * 初始化工作空间的管理员角色
     *
     * @param workspaceDTO
     */
    private RoleDTO initAdminRole(WorkspaceDTO workspaceDTO) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setWorkspaceId(workspaceDTO.getId());
        roleDTO.setName("管理员角色-请勿删除");
        roleDTO.setRoleType(RoleType.ADMIN);

        RoleDTO result = roleService.save(roleDTO);
        return result;
    }

    /**
     * 初始化工作空间的管理员用户
     *
     * @param workspaceDTO
     */
    private ProfileDTO initAdminUser(WorkspaceDTO workspaceDTO, RoleDTO adminRole) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.getRoles().add(adminRole);
        profileDTO.setWorkspaceId(workspaceDTO.getId());
        profileDTO.setName("管理员用户-请勿删除");
        profileDTO.setLoginName("admin-space" + workspaceDTO.getId());
        profileDTO.setPassword("123456");

        ProfileDTO result = profileService.save(profileDTO);
        return result;
    }
}
