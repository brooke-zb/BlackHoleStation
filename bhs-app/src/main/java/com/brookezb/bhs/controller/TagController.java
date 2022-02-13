package com.brookezb.bhs.controller;

import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private TagService tagService;

    @GetMapping("")
    public R<?> getTagList() {
        return R.success(tagService.selectAllWithHeat());
    }
}
