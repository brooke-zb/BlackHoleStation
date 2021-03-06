package com.brookezb.bhs.service;

import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.RoleMapper;
import com.brookezb.bhs.mapper.UserMapper;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.model.Role;
import com.brookezb.bhs.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleMapper roleMapper;
    private UserMapper userMapper;

    @Override
    public Role selectById(Long rid) {
        Role role = roleMapper.selectById(rid);
        if (role != null) {
            return role;
        }
        throw new NotFoundException("未找到该角色");
    }

    @Override
    public List<Role> selectAll() {
        List<Long> ids = roleMapper.selectAll();
        if (ids.isEmpty()) {
            return List.of();
        }
        return PageUtils.selectPage(ids, roleMapper::selectAllByIdList, Role.class);
    }

    @Override
    @Transactional
    public void insert(Role role) {
        if (roleMapper.insert(role) > 0) {
            Role newOne = roleMapper.selectByName(role.getName());
            if (newOne != null) {
                if (!role.getPermissions().isEmpty()) {
                    roleMapper.insertPermissionList(newOne.getRid(), role.getPermissions());
                }
                return;
            }
            throw new InvalidException("未知错误，角色插入失败");
        }
        if (roleMapper.selectByName(role.getName()) != null) {
            throw new InvalidException("该角色已存在");
        }
        throw new InvalidException("未知错误，角色插入失败");
    }

    @Override
    @Transactional
    public void update(Role role) {
        if (roleMapper.verifyRole(role.getRid()) != null) {
            if (role.getName() != null) {
                roleMapper.update(role);
            }

            roleMapper.deletePermissionNotInList(role.getRid(), role.getPermissions());
            if (!role.getPermissions().isEmpty()) {
                roleMapper.insertPermissionList(role.getRid(), role.getPermissions());
            }
            return;
        }
        throw new NotFoundException("未找到该角色");
    }

    @Override
    @Transactional
    public void delete(Long rid) {
        if (userMapper.selectCountByRoleId(rid) > 0) {
            throw new InvalidException("该角色下存在用户，无法删除");
        }
        if (roleMapper.verifyRole(rid) == null) {
            throw new NotFoundException("未找到该角色");
        }
        roleMapper.deletePermissionsById(rid);
        roleMapper.delete(rid);
    }
}
