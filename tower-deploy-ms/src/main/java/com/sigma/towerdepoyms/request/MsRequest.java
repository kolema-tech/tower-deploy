package com.sigma.towerdepoyms.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author zhenpeng
 */
@Getter
@Setter
@ApiModel(value = "微服務請求")
public class MsRequest {

    @ApiModelProperty(value = "名稱")
    @NotNull
    private String name;

    @ApiModelProperty(value = "git地址")
    @NotNull
    @URL
    private String gitUrl;

    @ApiModelProperty(value = "配置")
    private Map<String,Object> configs;
}
