package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface TagMapper {
    Tag selectById(Long tid);

    Tag selectByName(String name);

    List<Tag> selectAll();

    List<Tag> selectAllByList(List<Tag> tags);

    List<Tag> selectAllByAid(Long aid);

    int insert(Tag tag);

    int insertList(List<Tag> tags);

    int insertRelationByAid(@Param("aid") Long aid, @Param("tags") List<Tag> tags);

    int update(Tag tag);

    int delete(Long tid);

    int deleteRelationByTid(Long tid);

    int deleteList(List<Long> tids);

    int deleteRelationByTidList(List<Long> tids);
}
