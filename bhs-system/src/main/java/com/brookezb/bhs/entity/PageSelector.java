package com.brookezb.bhs.entity;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface PageSelector {
    public List<?> select(List<Long> ids);
}
