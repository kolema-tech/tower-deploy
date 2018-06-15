package com.sigma.towerdepoyms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/15-10:15
 * desc: 版本
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsVersion {
    /**
     * 版本名稱
     */
    private String version;

    private LocalDateTime commitTime;

    private String fullMessage;
}
