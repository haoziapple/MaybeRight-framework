package github.fast.xauth.web.rest.legacy;

import github.fast.xauth.domain.enumeration.ClientType;
import github.fast.xauth.service.TemplateQueryService;
import github.fast.xauth.service.TemplateService;
import github.fast.xauth.service.dto.TemplateDTO;
import github.fast.xauth.web.rest.errors.BadRequestAlertException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static github.fast.xauth.web.rest.legacy.LegacyConstants.LEGACY_PATH;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-06 19:47
 */
@RestController
@RequestMapping(LEGACY_PATH + "/template")
public class TemplateController {
    private final Logger log = LoggerFactory.getLogger(TemplateController.class);

    private final TemplateService templateService;

    private final TemplateQueryService templateQueryService;

    public TemplateController(TemplateService templateService, TemplateQueryService templateQueryService) {
        this.templateService = templateService;
        this.templateQueryService = templateQueryService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有模板")
    public ActionResult list() {
        return new ActionResult(templateQueryService.findByCriteria(null));
    }

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ApiOperation(value = "查看模板信息")
    public ActionResult item(@RequestParam("id") String id) {
        // TODO 为空时返回错误码
        return new ActionResult(templateService.findOne(Long.valueOf(id)).orElse(null));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增一个模板")
    public ActionResult insert(@RequestBody TemplateDTO templateDTO) {
        log.debug("REST request to add Template : {}", templateDTO);
        if (templateDTO.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", "xauthTemplate", "idexists");
        }

        if (templateDTO.getClientType() == null) {
            // 设置默认ClientType为PC
            templateDTO.setClientType(ClientType.PC);
        }

        TemplateDTO result = templateService.save(templateDTO);

        return new ActionResult(result);
    }
}
