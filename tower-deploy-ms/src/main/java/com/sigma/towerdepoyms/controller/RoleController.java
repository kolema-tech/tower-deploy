package com.sigma.towerdepoyms.controller;

import com.sigma.towerdepoyms.config.DeployConfig;
import com.sigma.towerdepoyms.entity.CopyConfig;
import com.sigma.towerdepoyms.entity.EurekaoutConfig;
import com.sigma.towerdepoyms.entity.NginxoutConfig;
import com.sigma.towerdepoyms.entity.RoleTree;
import com.sigma.towerdepoyms.response.Response;
import com.sigma.towerdepoyms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhenpeng
 */
@RestController
@RequestMapping(value = "/api/role/")
@Api(value = "角色控制器")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    private DeployConfig deployConfig;

    public static void callScript(String shellDir, String shellFile) {
        ProcessBuilder pb = new ProcessBuilder("./" + shellFile);
        pb.directory(new File(shellDir));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            runningStatus = p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (runningStatus != 0) {
            System.out.println("失敗！");
        }
    }

    @ApiOperation(value = "更新Role變量")
    @PutMapping(value = "/{role}/vars")
    public Response update(@PathVariable("role") String role, @RequestBody Map<String, Object> vars) {
        return Response.success();
    }

    @ApiOperation(value = "執行nginx-out Role")
    @PostMapping(value = "/nginx-out/")
    public Response executeNginxout(@RequestParam("ms") String name, @RequestParam("upstream_dir") String upstreamDir, @RequestParam("upstreams") List<String> upstreams) throws IOException {

        var config = new NginxoutConfig();
        config.setMs(name);
        config.setUpstream_dir(upstreamDir);
        config.setUpstreams(upstreams);

        roleService.nginxout(config);

        callScript(deployConfig.getAnsiblePath(), new File(deployConfig.getAnsiblePath(), "nginx-out.sh").getAbsolutePath());

        return Response.success();
    }

    @ApiOperation(value = "執行eureka-out")
    @PostMapping(value = "/eureka-out/")
    public Response executeEurekaout(@RequestParam("ms") String name, @RequestParam("eureka_url") String eurekaUrl, @RequestParam("instance_id") String instanceId) throws IOException {

        //執行 nginx-out.sh

        File varFile = new File(deployConfig.getAnsiblePath(), "ansible/roles/eureka-out/vars/main.yml");

        Yaml yaml = new Yaml();

        EurekaoutConfig eurekaoutConfig = yaml.loadAs(new FileInputStream(varFile), EurekaoutConfig.class);
        eurekaoutConfig.setMs(name);
        eurekaoutConfig.setEureka_url(eurekaUrl);
        eurekaoutConfig.setInstance_id(instanceId);

        String str = yaml.dumpAs(eurekaoutConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK);

        Files.write(Paths.get(varFile.getAbsolutePath()), str.getBytes());

        callScript(deployConfig.getAnsiblePath(), new File(deployConfig.getAnsiblePath(), "eureka-out.sh").getAbsolutePath());

        return Response.success();
    }

    @ApiOperation(value = "執行copy-file")
    @PostMapping(value = "/copy-file/")
    public Response executeCopy(@RequestParam("src_file") String srcFile, @RequestParam("dest_file") String destFile) throws IOException {

        var config = new CopyConfig();
        config.setSrc_file(srcFile);
        config.setDest_file(destFile);

        roleService.copy(config);

        callScript(deployConfig.getAnsiblePath(), new File(deployConfig.getAnsiblePath(), "copy-file.sh").getAbsolutePath());

        return Response.success();
    }

    @ApiOperation(value = "查詢版本")
    @GetMapping(value = "/getRoleTree")
    public Response getRoleTree() {

        File file = new File(deployConfig.getAnsiblePath());

        RoleTree roleTree = new RoleTree();
        travelFile(file, "", roleTree);


        return new Response(20000, "查詢成功", roleTree);
    }

    @ApiOperation(value = "查詢版本")
    @GetMapping(value = "/getRoleContent")
    public Response getRoleFile(@RequestParam("file") String file) throws IOException {

        var list = Files.readAllLines(Paths.get(deployConfig.getAnsiblePath(), file));

        StringBuilder sb = new StringBuilder();

        list.forEach(zw -> {
            sb.append(System.lineSeparator() + zw);
        });

        return new Response(20000, "查詢成功", sb.toString());
    }

    /**
     * 遍歷樹
     *
     * @param file 文件
     * @param root 根
     */
    private void travelFile(File file, String parent, RoleTree root) {

        root.setParent(parent);
        root.setIsLeaf(false);
        root.setLabel(file.getName());
        if (root.getChildren() == null) {
            root.setChildren(new ArrayList<>());
        }

        for (File file1 : file.listFiles()) {
            //是目錄
            RoleTree roleTree = new RoleTree();
            roleTree.setLabel(file1.getName());
            roleTree.setParent(parent);

            if (file1.isDirectory()) {
                roleTree.setIsLeaf(false);
                root.getChildren().add(roleTree);
                travelFile(file1, parent + "/" + file1.getName(), roleTree);
            } else {
                roleTree.setIsLeaf(true);
                root.getChildren().add(roleTree);
            }
        }
    }
}
