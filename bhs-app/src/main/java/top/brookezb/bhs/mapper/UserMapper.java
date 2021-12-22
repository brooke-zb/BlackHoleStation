package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.brookezb.bhs.model.User;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface UserMapper {
    User selectById(Long uid);

    User selectByUsername(String username);

    List<User> selectAll();

    int insert(User user);

    int update(User user);

    int delete(Long uid);
}
