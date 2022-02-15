package com.brookezb.bhs.mapper;

import com.brookezb.bhs.entity.ArticleTimeline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.model.Tag;
import com.brookezb.bhs.model.User;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface ArticleMapper {
    Article selectById(Long aid);

    List<Long> selectAll(@Param("uid") Long uid, @Param("cid") Long cid, @Param("tid") Long tid, @Param("status") Article.Status status);

    List<Long> selectAllByTagName(@Param("name") String name, @Param("status") Article.Status status);

    List<Article> selectAllByIdList(List<Long> aids);

    List<ArticleTimeline> selectAllTimeline();

    Integer selectCountByCategoryId(Long cid);

    Integer verifyArticle(Long aid);

    User selectUserById(Long aid);

    int insert(Article article);

    int insertTags(@Param("aid") Long aid, @Param("tags") List<Tag> tags);

    int update(Article article);

    int delete(Long aid);

    int deleteTagsNotInList(@Param("aid") Long aid, @Param("tags") List<Tag> tags);
}
