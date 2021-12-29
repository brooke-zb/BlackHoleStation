package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.Article;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface ArticleMapper {
    Article selectById(@Param("id") Long id, @Param("chooseDelayed") boolean chooseDelayed, @Param("published") boolean published);

    List<Article> selectAll(@Param("title") String title, @Param("published") Boolean published);

    List<String> selectTags(Long id);

    int insert(Article article);

    int update(Article article);

    int delete(Long id);
}
