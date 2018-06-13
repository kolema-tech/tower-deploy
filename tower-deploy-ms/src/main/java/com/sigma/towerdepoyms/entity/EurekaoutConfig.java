package com.sigma.towerdepoyms.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhenpeng
 */
@Getter
@Setter
@ToString
public class EurekaoutConfig {
    private String ms;
    private String eureka_url;
    private String instance_id;
}
