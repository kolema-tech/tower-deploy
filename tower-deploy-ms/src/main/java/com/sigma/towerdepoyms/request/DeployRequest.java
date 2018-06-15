package com.sigma.towerdepoyms.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/15-16:35
 * desc:
 **/
@Getter
@Setter
public class DeployRequest {
    private String upstreamDir;
    private String upstreams;
    private String srcFile;
    private String destFile;
}
