package com.brookezb.bhs.mapper;

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

    List<Article> selectAll(boolean hashMail);

    List<Article> selectAllByStatus(@Param("status") Article.Status status, @Param("hashMail") boolean hashMail);

    List<Article> selectAllByCategoryId(@Param("cid")Long cid, @Param("hashMail") boolean hashMail);

    List<Article> selectAllByUserId(@Param("uid")Long uid, @Param("hashMail") boolean hashMail);

    List<Article> selectAllByTagName(@Param("name")String name, @Param("hashMail") boolean hashMail);

    Integer selectCountByCategoryId(Long cid);

    Integer verifyArticle(Long aid);

    User selectUserById(Long aid);

    int insert(Article article);

    int insertTags(@Param("aid") Long aid, @Param("tags") List<Tag> tags);

    int update(Article article);

    int delete(Long aid);

    int deleteTagsNotInList(@Param("aid") Long aid, @Param("tags") List<String> tags);
}
