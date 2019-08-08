package github.fast.xauth.web.rest.legacy.init;

import github.fast.xauth.service.WorkspaceService;
import github.fast.xauth.service.dto.WorkspaceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-07 9:56
 */
@Component
public class AuthInitRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(AuthInitRunner.class);

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Autowired
    private WorkspaceService workspaceService;

    @Override
    public void run(String... args) throws Exception {
        AuthInitProperties initProperties = null;
        InputStream fs = null;
        try {
            fs = new ClassPathResource("authinit" + File.separator + "init.json").getInputStream();
            String str = StreamUtils.copyToString(fs, Charset.forName("UTF-8"));
            initProperties = converter.getObjectMapper().readValue(str, AuthInitProperties.class);
        } catch (FileNotFoundException e) {
            log.error("cannot find file", e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        if (initProperties == null) {
            return;
        }

        //  初始化工作空间
        WorkspaceDTO rootSpace = workspaceService.save(initProperties.getRootSpace());
    }

}
