package com.brookezb.bhs.controller;

import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author brooke_zb
 */
@AllArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendController {
    private FriendService friendService;

    @GetMapping("/{id:\\d+}")
    public R<?> getFriend(@PathVariable("id") Long id) {
        return R.success(friendService.selectById(id));
    }

    @GetMapping("")
    public R<?> getAllFriends() {
        return R.success(friendService.selectAll());
    }
}
