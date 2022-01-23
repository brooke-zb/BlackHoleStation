package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.brookezb.bhs.model.Category;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface CategoryMapper {
    Category selectById(Long cid);

    List<Category> selectAll();

    int insert(Category category);

    int update(Category category);

    int delete(Long cid);

    int deleteList(List<Long> cids);
}
