package com.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.brookezb.bhs.model.User;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface UserMapper {
    User selectById(Long uid);

    User selectByName(String name);

    List<Long> selectAll(@Param("username") String username, @Param("enabled") Boolean enabled);

    List<User> selectAllByIdList(List<Long> uids);

    Integer selectCountByRoleId(Long rid);

    Integer verifyUser(Long uid);

    int insert(User user);

    int update(User user);

    int updateStatus(@Param("uid") Long uid, @Param("enabled") Boolean enabled);

    int delete(Long uid);
}
