package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Friend;
import com.brookezb.bhs.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author brooke_zb
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/friend")
public class FriendAdminController {
    private FriendService friendService;

    @PostMapping("")
    @RequirePermission("FRIEND:ADD")
    public R<?> addFriend(@RequestBody @Validated(Friend.Add.class) Friend friend) {
        friendService.insert(friend);
        return R.success(null, "添加友链成功");
    }

    @PutMapping("")
    @RequirePermission("FRIEND:UPDATE")
    public R<?> updateFriend(@RequestBody @Validated(Friend.Update.class) Friend friend) {
        friendService.update(friend);
        return R.success(null, "更新友链成功");
    }

    @DeleteMapping("/{id:\\d+}")
    @RequirePermission("FRIEND:DELETE")
    public R<?> deleteFriend(@PathVariable("id") Long id) {
        friendService.delete(id);
        return R.success(null, "删除友链成功");
    }
}
