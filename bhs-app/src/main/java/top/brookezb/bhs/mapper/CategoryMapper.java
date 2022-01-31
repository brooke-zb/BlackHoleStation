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

    Category selectByName(String name);

    Category selectByParent(Long parent);

    List<Category> selectAll();

    Integer verifyCategory(Long cid);

    int insert(Category category);

    int update(Category category);

    void removeParentByCid(Long cid);

    int delete(Long cid);
}
