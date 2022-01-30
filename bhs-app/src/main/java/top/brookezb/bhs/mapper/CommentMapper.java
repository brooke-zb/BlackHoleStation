package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.brookezb.bhs.model.Comment;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface CommentMapper {
    Comment selectById(Long coid);

    List<Comment> selectAll(Comment.Status status);

    List<Comment> selectAllByArticleId(Long aid);

    List<Comment> selectByNickname(String name);

    List<Comment> selectByIp(String ip);

    int insert(Comment comment);

    int update(Comment comment);

    int delete(Long coid);

    int deleteList(List<Long> coids);
}
