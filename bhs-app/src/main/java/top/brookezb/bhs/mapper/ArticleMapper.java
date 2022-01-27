package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.Article;
import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface ArticleMapper {
    Article selectById(Long aid);

    List<Article> selectAll();

    List<Article> selectAllByStatus(Article.Status status);

    List<Article> selectAllByCategoryId(Long cid);

    Integer selectCountByCategoryId(Long cid);

    List<Article> selectAllByUserId(Long uid);

    List<Article> selectAllByTagName(String name);

    int insert(Article article);

    int insertTags(@Param("aid") Long aid, @Param("tags") List<Tag> tags);

    int update(Article article);

    int delete(Long aid);

    int deleteList(List<Long> aids);

    int deleteTagsNotInList(@Param("aid") Long aid, @Param("tags") List<String> tags);
}
