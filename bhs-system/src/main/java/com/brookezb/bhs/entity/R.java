package com.brookezb.bhs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * API响应结果包装类
 * @author brooke_zb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /* 是否成功 */
    private boolean success;
    /* 承载数据 */
    private T data;
    /* 返回消息 */
    private String msg;

    /**
     * 操作成功，返回消息
     */
    public static <T> R<T> success() {
        return success(null, "请求成功");
    }

    /**
     * 操作成功，返回数据
     * @param data 数据
     */
    public static <T> R<T> success(T data) {
        return success(data, "请求成功");
    }

    /**
     * 操作成功，返回数据
     * @param data 数据
     * @param msg 返回消息
     */
    public static <T> R<T> success(T data, String msg) {
        return new R<>(true, data, msg);
    }

    /**
     * 操作失败，返回默认消息
     */
    public static <T> R<T> fail() {
        return fail("请求失败");
    }

    /**
     * 操作失败，返回消息
     * @param msg 返回消息
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(false, null, msg);
    }
}
