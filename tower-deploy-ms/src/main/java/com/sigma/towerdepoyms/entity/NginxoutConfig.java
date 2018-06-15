package com.sigma.towerdepoyms.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author zhenpeng
 */
@Getter
@Setter
@ToString
public class NginxoutConfig {
    /**
     * 服務名稱
     */
    private String ms;

    /**
     * upstream的目錄
     */
    private String upstream_dir;

    /**
     * upstream的配置
     */
    private List<String> upstreams;
}
