package com.sigma.towerdepoyms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/15-17:14
 * desc: 角色樹
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleTree {
    /**
     * id
     */
    private String id;

    /**
     * label
     */
    private String label;

    /**
     * 是否為葉子節點
     */
    private Boolean isLeaf;

    private List<RoleTree> children;
}