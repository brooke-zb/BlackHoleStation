package com.brookezb.bhs.mapper;

import com.brookezb.bhs.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface CategoryMapper {
    Category selectById(Long cid);

    List<Category> selectAll();

    Integer verifyName(String name);

    Integer verifyCategory(Long cid);

    int insert(Category category);

    int update(Category category);

    void removeParentByCid(Long cid);

    int delete(Long cid);
}
