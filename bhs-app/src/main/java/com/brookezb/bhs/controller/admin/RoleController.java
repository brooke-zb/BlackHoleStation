package com.brookezb.bhs.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.RoleService;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/admin/role")
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @GetMapping("/{id:\\d+}")
    public R<?> getRole(@PathVariable Long id) {
        return R.success(roleService.selectById(id));
    }
}
