package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.brookezb.bhs.model.Role;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface RoleMapper {
    Role selectById(Long rid);

    List<String> selectPermissions(Long rid);

    int insert(Role role);

    int delete(Long rid);

    int deletePermission(@Param("rid") Long rid, @Param("permission") String permission);

    int clearPermission(Integer rid);

    int insertPermission(@Param("rid") Long rid, @Param("permission") String permission);
}
