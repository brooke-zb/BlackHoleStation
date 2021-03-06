package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Category;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface CategoryService {
    Category selectById(Long cid);

    List<Category> selectAll();

    void insert(Category category);

    void update(Category category);

    void delete(Long cid);
}
