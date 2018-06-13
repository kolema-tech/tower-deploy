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
    private String ms;
    private String upstream_dir;
    private List<String> upstreams;
}
