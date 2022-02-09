package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/admin/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id:\\d+}")
    @RequirePermission("USER:GET")
    public R<?> getUser(@PathVariable Long id) {
        return R.success(userService.selectById(id));
    }

    /**
     * 分页查询用户列表
     *
     * @param page     当前页
     * @param username 用户名
     * @param enabled  是否启用
     * @return 查询结果
     */
    @GetMapping("")
    @RequirePermission("USER:GET")
    public R<?> getUserList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") Integer page,
            @RequestParam(defaultValue = "10") @Pattern(regexp = "^[123]0$", message = "页数需为10/20/30") String size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Boolean enabled
    ) {
        PageHelper.startPage(page, Integer.parseInt(size));
        return R.success(
                PageInfo.of(userService.selectAll(username, enabled))
        );
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 新增结果
     */
    @PostMapping("")
    @RequirePermission("USER:ADD")
    public R<?> addUser(@RequestBody @Validated(User.Add.class) User user) {
        userService.insert(user);
        return R.success(null, "添加用户成功");
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("")
    @RequirePermission("USER:UPDATE")
    public R<?> updateUser(@RequestBody @Validated(User.Update.class) User user) {
        userService.update(user);
        return R.success(null, "更新用户成功");
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除结果
     */
    @RequirePermission("USER:DELETE")
    @DeleteMapping("/{id:\\d+}")
    public R<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return R.success(null, "删除用户成功");
    }

    /**
     * 更新用户状态
     *
     * @param id     用户id
     * @param status 状态
     * @return 更新结果
     */
    @RequirePermission("USER:UPDATE")
    @PatchMapping("/{id:\\d+}/status/{status:^true$|^false$}")
    public R<?> updateStatus(@PathVariable Long id, @PathVariable Boolean status) {
        userService.updateStatus(id, status);
        return R.success(null, "更新用户状态成功");
    }

}
