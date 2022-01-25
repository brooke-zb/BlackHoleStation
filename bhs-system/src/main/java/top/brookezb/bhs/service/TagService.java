package top.brookezb.bhs.service;

import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface TagService {
    Tag selectById(Long tid);

    List<Tag> selectAll();

    List<Tag> selectAllByNameList(List<String> names);

    List<Tag> selectAllByAid(Long aid);

    boolean insert(Tag tag);

    boolean insertList(List<Tag> tags);

    boolean update(Tag tag);

    boolean delete(Long tid);

    boolean deleteList(List<Long> tids);
}
