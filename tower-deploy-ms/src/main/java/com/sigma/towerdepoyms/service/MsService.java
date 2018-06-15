package com.sigma.towerdepoyms.service;

import com.sigma.towerdepoyms.entity.Ms;
import com.sigma.towerdepoyms.entity.MsVersion;
import com.sigma.towerdepoyms.request.MsRequest;

import java.util.List;

/**
 * @author zhenpeng
 */
public interface MsService {

    /**
     * 提交微服務
     * @param msRequest
     * @return
     */
    int insertMs(MsRequest msRequest);

    /**
     * 更新微服務
     * @param msRequest
     * @return
     */
    int updateMs(MsRequest msRequest);

    /**
     * 查詢微服務
     * @return
     */
    List<Ms> getAll();

    /**
     * 獲取版本
     * @return
     * @param name
     */
    List<MsVersion> getVersions(String name);

    /**
     * 根據名稱查找
     * @param msName
     * @return
     */
    Ms find(String msName);
}
