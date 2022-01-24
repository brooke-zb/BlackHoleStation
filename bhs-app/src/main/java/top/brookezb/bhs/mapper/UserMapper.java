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

    int updateStatus(@Param("uid") Long uid, @Param("enabled") Boolean enabled);

    int updateStatusList(@Param("uids") List<Long> uids, @Param("enabled") Boolean enabled);

    int delete(Long uid);

    int deleteList(List<Long> uids);
}
