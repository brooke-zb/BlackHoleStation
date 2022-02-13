package com.brookezb.bhs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 标签表
 */
@Data
public class Tag {
    /**
     * 标签id
     */
    @NotNull(message = "标签id不能为空", groups = Update.class)
    private Long tid;

    /**
     * 标签名称
     */
    @NotNull(message = "标签名不能为空", groups = {Add.class, Update.class})
    private String name;

    /**
     * 标签热度
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer heat;

    public interface Add extends Default {}
    public interface Update extends Default {}
}