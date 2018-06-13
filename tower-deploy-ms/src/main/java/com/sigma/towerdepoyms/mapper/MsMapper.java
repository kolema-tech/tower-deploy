package com.sigma.towerdepoyms.mapper;

import com.sigma.towerdepoyms.entity.Ms;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhenpeng
 */
@Component
public interface MsMapper {
    /**
     * 按照主鍵刪除
     * @param id 主鍵
     * @return 影響行數
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record 微服務對象
     * @return 影響行數
     */
    int insert(Ms record);

    /**
     * 選擇插入
     * @param record 微服務對象
     * @return 影響行數
     */
    int insertSelective(Ms record);

    /**
     * 按照主鍵查找
     * @param id 主鍵
     * @return 影響行數
     */
    Ms selectByPrimaryKey(Integer id);

    /**
     * 根據名稱查詢MS記錄
     * @param name 微服務名稱
     * @return 對象
     */
    Ms selectByName(String name);

    /**
     * 按照主鍵條件更新
     * @param record 對象
     * @return  影響行數
     */
    int updateByPrimaryKeySelective(Ms record);

    /**
     * 按照主鍵更新
     * @param record 對象
     * @return 影響行數
     */
    int updateByPrimaryKey(Ms record);

    /**
     * 查詢全部
     * @return
     */
    List<Ms> selectAll();
}