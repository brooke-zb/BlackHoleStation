package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface TagService {
    Tag selectById(Long tid);

    List<Tag> selectAll();

    List<Tag> selectAllWithHeat();

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long tid);

    int deleteList(List<Long> tids);
}
