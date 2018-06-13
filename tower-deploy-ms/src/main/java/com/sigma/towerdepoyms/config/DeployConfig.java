package com.sigma.towerdepoyms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhenpeng
 */
@Configuration
@ConfigurationProperties
@Getter
@Setter
public class DeployConfig {

    /**
     * GIT包管理地址
     */
    @Value("${git.path}")
    String gitPath;

    /**
     * ANSIBLE 主目錄
     */
    @Value("${ansible.path}")
    String ansiblePath;

    @Value("${git.username}")
    String username;

    @Value("${git.password}")
    String password;
}
