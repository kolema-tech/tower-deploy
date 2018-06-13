package com.sigma.towerdepoyms.service;

import com.sigma.towerdepoyms.entity.CopyConfig;
import com.sigma.towerdepoyms.entity.NginxoutConfig;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author zhenpeng
 */
public interface RoleService {

    void nginxout(NginxoutConfig nginxoutConfig) throws IOException;

    void copy(CopyConfig copyConfig) throws IOException;
}
