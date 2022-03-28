package com.brookezb.bhs.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author brooke_zb
 */
@Data
public class Friend {
    /**
     * 友链id
     */
    @NotNull(message = "友链id不能为空", groups = Update.class)
    private Long fid;

    /**
     * 站点名称
     */
    @NotNull(message = "站点名称不能为空", groups = Add.class)
    private String name;

    /**
     * 站点链接
     */
    @NotNull(message = "站点链接不能为空", groups = Add.class)
    private String link;

    /**
     * 图标链接
     */
    @NotNull(message = "站点图标不能为空", groups = Add.class)
    private String avatar;

    /**
     * 网站介绍
     */
    private String description;

    public interface Add extends Default {}
    public interface Update extends Default {}
}
