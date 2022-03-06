package com.brookezb.bhs.utils;

import com.brookezb.bhs.entity.PageSelector;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author brooke_zb
 */
@SuppressWarnings("unchecked")
public class PageUtils {
    public static <T> List<T> selectPage(List<Long> ids, PageSelector selector, Class<T> clazz) {
        if (ids instanceof Page page) {
            List<?> result = selector.select(page);
            page.clear();
            page.addAll(result);
            return page;
        }
        return (List<T>) selector.select(ids);
    }
}
