package com.sigma.towerdepoyms.controller;

import com.sigma.towerdepoyms.config.DeployConfig;
import com.sigma.towerdepoyms.entity.CopyConfig;
import com.sigma.towerdepoyms.entity.NginxoutConfig;
import com.sigma.towerdepoyms.response.Response;
import com.sigma.towerdepoyms.service.MsService;
import com.sigma.towerdepoyms.service.RoleService;
import com.sigma.towerdepoyms.util.git.CheckoutFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhenpeng
 */
@RestController
@RequestMapping(value = "/api/deploy/")
@Api(value = "部署控制器")
public class DeployController {

    @Autowired
    DeployConfig deployConfig;

    @Autowired
    CheckoutFile checkoutFile;

    @Autowired
    MsService msService;

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "執行部署")
    @PostMapping(value = "/{ms}/{version}")
    public Response deploy(@NotBlank @PathVariable("ms") String name,
                           @NotBlank @PathVariable("version") String version,
                           @RequestParam("upstream_dir") String upstreamDir,
                           @RequestParam("upstreams") List<String> upstreams,
                           @RequestParam("src_file") String srcFile,
                           @RequestParam("dest_file") String destFile) throws IOException, GitAPIException, InterruptedException {

        //check the ms exists.
        var ms = msService.find(name);
        if (ms == null) {
            return new Response(2, "不存在服務配置");
        }

        //check the git-package exists
        var file = new File(deployConfig.getGitPath(), name);
        if (!file.exists()) {
            return new Response(2, "git包路徑不存在");
        }

        //check out the version
        checkoutFile.gitCheckout(new File(deployConfig.getGitPath(), name), version);

        //nginx-out
        var nginxConfig = new NginxoutConfig();
        nginxConfig.setMs(name);
        nginxConfig.setUpstream_dir(upstreamDir);
        nginxConfig.setUpstreams(upstreams);

        roleService.nginxout(nginxConfig);

        //pause
        Thread.sleep(2000);

        //stop

        //copy
        var copyConfig = new CopyConfig();
        copyConfig.setSrc_file(srcFile);
        copyConfig.setDest_file(destFile);

        roleService.copy(copyConfig);

        //start

        return Response.success();
    }

}
