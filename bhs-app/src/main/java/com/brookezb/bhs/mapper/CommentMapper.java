package com.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.brookezb.bhs.model.Comment;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface CommentMapper {
    Comment selectById(@Param("coid") Long coid, @Param("isAdmin") boolean isAdmin);

    List<Comment> selectAll(@Param("aid") Long aid, @Param("ip") String ip, @Param("status") Comment.Status status);

    List<Long> selectAllByArticleId(Long aid);

    List<Comment> selectAllByNickname(String name);

    List<Comment> selectAllByIp(String ip);

    List<Comment> selectAllByIdList(List<Long> coids);

    String selectTrustEmail(String email);

    Integer verifyComment(Long coid);

    int insert(Comment comment);

    int update(Comment comment);

    int updateStatus(@Param("coid") Long coid, @Param("status") Comment.Status status);

    int updateStatusList(@Param("coids") List<Long> coids, @Param("status") Comment.Status status);

    int delete(Long coid);

    int deleteList(List<Long> coids);
}
