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

    int insert(Tag tag);

    int insertList(List<Tag> tags);

    int update(Tag tag);

    int delete(Long tid);

    int deleteList(List<Long> tids);
}
