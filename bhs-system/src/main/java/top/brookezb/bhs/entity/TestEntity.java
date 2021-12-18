package top.brookezb.bhs.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author brooke_zb
 */
@Data
public class TestEntity {
    /**
     * id
     */
    private Long tid;

    /**
     * 测试值
     */
    private String test;
}