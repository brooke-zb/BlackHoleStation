package top.brookezb.bhs.service;

import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface TagService {
    Tag selectById(Long tid);

    List<Tag> selectAll();

    boolean insert(Tag tag);

    boolean update(Tag tag);

    boolean delete(Long tid);

    boolean deleteList(List<Long> tids);
}
