package com.sigma.towerdepoyms.service;

import com.sigma.towerdepoyms.config.DeployConfig;
import com.sigma.towerdepoyms.entity.CopyConfig;
import com.sigma.towerdepoyms.entity.NginxoutConfig;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zhenpeng
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    DeployConfig deployConfig;

    @Override
    public void nginxout(NginxoutConfig config) throws IOException {
        File varFile = new File(deployConfig.getAnsiblePath(), "ansible/roles/nginx-out/vars/main.yml");

        Yaml yaml = new Yaml();

        var nginxoutConfig = yaml.loadAs(new FileInputStream(varFile), NginxoutConfig.class);
        nginxoutConfig.setMs(config.getMs());
        nginxoutConfig.setUpstream_dir(config.getUpstream_dir());
        nginxoutConfig.setUpstreams(config.getUpstreams());

        String str = yaml.dumpAs(nginxoutConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK);

        Files.write(Paths.get(varFile.getAbsolutePath()), str.getBytes());
    }

    @Override
    public void copy(CopyConfig config) throws IOException {

        File varFile = new File(deployConfig.getAnsiblePath(), "ansible/roles/copy-file/vars/main.yml");

        Yaml yaml = new Yaml();

        CopyConfig copyConfig = yaml.loadAs(new FileInputStream(varFile), CopyConfig.class);
        copyConfig.setSrc_file(config.getSrc_file());
        copyConfig.setDest_file(config.getDest_file());

        String str = yaml.dumpAs(copyConfig, Tag.MAP, DumperOptions.FlowStyle.BLOCK);

        Files.write(Paths.get(varFile.getAbsolutePath()), str.getBytes());

    }
}
