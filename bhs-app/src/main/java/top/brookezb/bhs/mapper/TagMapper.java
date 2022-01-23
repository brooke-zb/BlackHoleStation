package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface TagMapper {
    Tag selectById(Long tid);

    List<Tag> selectAll();

    Integer insert(Tag tag);

    Integer insertList(List<Tag> tags);

    Integer update(Tag tag);

    Integer delete(Long tid);

    Integer deleteList(List<Long> tids);
}
