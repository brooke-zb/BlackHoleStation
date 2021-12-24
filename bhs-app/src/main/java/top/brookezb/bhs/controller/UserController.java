package top.brookezb.bhs.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.service.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    /**
     * 分页查询用户列表
     *
     * @param page 当前页
     * @param size 每页大小
     * @param username 用户名
     * @param enabled 是否启用
     * @return 查询结果
     */
    @GetMapping("")
    public R<?> getUserList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") Integer page,
            @RequestParam(defaultValue = "10") @Pattern(regexp = "^[12345]0$", message = "分页大小只能为10的倍数，最大不超过50") String size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Boolean enabled) {
        return R.success(userService.selectAll(page, Integer.parseInt(size), username, enabled));
    }

    @GetMapping("/{id:\\d+}")
    public R<?> getUser(@PathVariable Long id) {
        return R.success(userService.selectById(id));
    }
}
