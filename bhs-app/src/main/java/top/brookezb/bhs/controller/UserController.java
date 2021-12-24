package top.brookezb.bhs.controller;

import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.model.Article;
import top.brookezb.bhs.service.UserService;

import javax.validation.constraints.Min;

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
     * @param page 当前页
     * @param size 每页大小
     * @return 查询结果
     */
    @GetMapping("")
    public R<?> getUserList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") Integer page,
            @RequestParam(defaultValue = "10") @Range(min = 10, max = 50, message = "分页大小只能在10至50之间") Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Boolean enabled) {
        return R.success(userService.selectAll(page, size, username, enabled));
    }
}
