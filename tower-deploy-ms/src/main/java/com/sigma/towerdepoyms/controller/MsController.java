package com.sigma.towerdepoyms.controller;

import com.sigma.towerdepoyms.config.DeployConfig;
import com.sigma.towerdepoyms.request.MsRequest;
import com.sigma.towerdepoyms.response.Response;
import com.sigma.towerdepoyms.service.MsService;
import com.sigma.towerdepoyms.util.git.CloneRemoteRepository;
import com.sigma.towerdepoyms.util.git.CommitFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author zhenpeng
 */
@RestController
@RequestMapping(value = "/api/ms/")
@Api(value = "微服務控制器")
public class MsController {

    @Autowired
    DeployConfig deployConfig;

    @Autowired
    MsService msService;

    @Autowired
    CloneRemoteRepository cloneRemoteRepository;

    @Autowired
    CommitFile commitFile;

    @ApiOperation(value = "創建一個微服務")
    @PostMapping(value = "/")
    public Response create(@Validated @RequestBody MsRequest msRequest) {

        msService.insertMs(msRequest);

        return Response.success();
    }

    @ApiOperation(value = "更新一個微服務")
    @PutMapping(value = "/")
    public Response update(@Validated @RequestBody MsRequest msRequest) {

        msService.updateMs(msRequest);

        return Response.success();
    }

    @ApiOperation(value = "查詢微服務")
    @GetMapping(value = "/")
    public Response getAll() {
        return new Response(0, "查詢成功", msService.getAll());
    }

    @ApiOperation(value = "上傳包")
    @PostMapping(value = "/upload/{ms}")
    public Response update(@NotBlank @PathVariable("ms") String name, @NotBlank @RequestParam("commitMessage") String commitMessage, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws GitAPIException {

        var ms = msService.find(name);
        if (ms == null) {
            return new Response(2, "ms不存在！");
        }

        if (file.isEmpty()) {
            return new Response(2, "文件為空！");
        }

        var fileName = file.getOriginalFilename();

        var saveFile = new File(deployConfig.getGitPath(), name + "/" + fileName);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();

            cloneRemoteRepository.cloneRemote(name, ms.getGitUrl());
        }

        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
            out.write(file.getBytes());
            out.flush();
            out.close();

            commitFile.commitAndPush(saveFile.getParentFile(), fileName, commitMessage);

            return new Response(0, "上傳成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(2, "上传失败," + e.getMessage());
        }
    }
}
