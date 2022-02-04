package com.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.brookezb.bhs.model.Role;

import java.util.List;
import java.util.Set;

/**
 * @author brooke_zb
 */
@Mapper
public interface RoleMapper {
    Role selectById(Long rid);

    Role selectByName(String name);

    List<Role> selectAll();

    int insert(Role role);

    int insertPermissionList(@Param("rid") Long rid, @Param("permissions") Set<String> permissions);

    int update(Role role);

    int delete(Long rid);

    int deletePermissionsById(Long rid);

    int deletePermissionsByIdList(List<Long> rids);

    int deletePermissionNotInList(@Param("rid") Long rid, @Param("permissions") Set<String> permissions);
}
