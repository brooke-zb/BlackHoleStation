package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.Role;

import java.util.List;
import java.util.Set;

/**
 * @author brooke_zb
 */
@Mapper
public interface RoleMapper {
    Role selectById(Long rid);

    List<Role> selectAll();

    int insert(Role role);

    int update(Role role);

    int delete(Long rid);

    int deleteList(List<Long> rids);

    int deletePermissionNotInList(@Param("rid") Long rid, @Param("permissions") List<String> permissions);

    int insertPermissionList(@Param("rid") Long rid, @Param("permissions") List<String> permissions);
}
