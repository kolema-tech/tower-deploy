package com.sigma.towerdepoyms.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhenpeng
 */
@Getter
@Setter
@ToString
@ApiModel(value = "響應")
public class Response {
    @ApiModelProperty(value = "響應消息")
    private String message;

    @ApiModelProperty(value = "響應代碼：為0表示成功")
    private int code;

    @ApiModelProperty(value = "數據")
    private Object data;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success() {
        return new Response(0, "");
    }
}
