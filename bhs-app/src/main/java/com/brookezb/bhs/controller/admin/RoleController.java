package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.model.Role;
import com.brookezb.bhs.validation.RoleGroup;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.RoleService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

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

    @GetMapping("")
    public R<?> getRoleList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") Integer page,
            @RequestParam(defaultValue = "10") @Pattern(regexp = "^[123]0$", message = "页数需为10/20/30") String size
    ) {
        PageHelper.startPage(page, Integer.parseInt(size));
        return R.success(
                PageInfo.of(roleService.selectAll())
        );
    }

    @PostMapping("")
    public R<?> addRole(@RequestBody @Validated(RoleGroup.Add.class) Role role) {
        roleService.insert(role);
        return R.success(null, "添加角色成功");
    }

    @PutMapping("")
    public R<?> updateRole(@RequestBody @Validated(RoleGroup.Update.class) Role role) {
        roleService.update(role);
        return R.success(null, "更新角色成功");
    }

    @DeleteMapping("/{id:\\d+}")
    public R<?> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return R.success(null, "删除角色成功");
    }
}
