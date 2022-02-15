package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequireAuth;
import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Tag;
import com.brookezb.bhs.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author brooke_zb
 */
@RequireAuth
@RestController
@RequestMapping("/admin/tag")
@AllArgsConstructor
public class TagAdminController {
    private TagService tagService;

    @GetMapping("")
    public R<?> getTagList() {
        return R.success(tagService.selectAll());
    }

    @PostMapping("")
    @RequirePermission("TAG:ADD")
    public R<?> addTag(@RequestBody @Validated(Tag.Add.class) Tag tag) {
        tagService.insert(tag);
        return R.success(null, "添加标签成功");
    }

    @PutMapping("")
    @RequirePermission("TAG:UPDATE")
    public R<?> updateTag(@RequestBody @Validated(Tag.Update.class) Tag tag) {
        tagService.update(tag);
        return R.success(null, "更新标签成功");
    }

    @DeleteMapping("/{id:\\d+}")
    @RequirePermission("TAG:DELETE")
    public R<?> deleteTag(@PathVariable("id") Long id) {
        tagService.delete(id);
        return R.success(null, "删除标签成功");
    }

    @DeleteMapping("/{ids:\\d+(?:,\\d+)+}")
    @RequirePermission("TAG:DELETE")
    public R<?> deleteTagList(@PathVariable String ids) {
        List<Long> list = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        int result = tagService.deleteList(list);
        return R.success(null, "删除了" + result + "个标签");
    }
}
