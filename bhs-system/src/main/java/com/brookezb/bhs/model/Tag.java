package com.brookezb.bhs.model;

import lombok.Data;

/**
 * 标签表
 */
@Data
public class Tag {
    /**
     * 标签id
     */
    private Long tid;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签热度
     */
    private Integer heat;
}