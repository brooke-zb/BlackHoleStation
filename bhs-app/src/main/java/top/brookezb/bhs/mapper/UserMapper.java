package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.User;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface UserMapper {
    User selectById(Long uid);

    User selectByUsername(String username);

    List<User> selectAll(@Param("username") String username, @Param("enabled") Boolean enabled);

    int insert(User user);

    int update(User user);

    int delete(Long uid);
}
