package top.brookezb.bhs.model;

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