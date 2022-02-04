package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Role;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface RoleService {
    Role selectById(Long rid);

    List<Role> selectAll();

    void insert(Role role);

    void update(Role role);

    void delete(Long rid);
}
