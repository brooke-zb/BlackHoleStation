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
    Article selectById(@Param("id") Long id, @Param("compareTime") boolean compareTime, @Param("enabled") boolean enabled);

    List<Article> selectAll(@Param("title") String title, @Param("enabled") Boolean enabled);

    List<String> selectTags(Long id);

    int insert(Article article);
}
