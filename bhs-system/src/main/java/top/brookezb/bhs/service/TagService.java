package top.brookezb.bhs.service;

import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface TagService {
    Tag selectById(Long tid);

    List<Tag> selectAll();

    void insert(Tag tag);

    void update(Tag tag);

    void delete(Long tid);

    int deleteList(List<Long> tids);
}
