package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Category;
import com.brookezb.bhs.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/admin/category")
@AllArgsConstructor
public class CategoryAdminController {
    private CategoryService categoryService;

    @PostMapping("")
    @RequirePermission("CATEGORY:ADD")
    public R<?> addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.insert(category);
        return R.success(null, "添加分类成功");
    }

    @PutMapping("")
    @RequirePermission("CATEGORY:UPDATE")
    public R<?> updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return R.success(null, "更新分类成功");
    }

    @DeleteMapping("/{id:\\d+}")
    @RequirePermission("CATEGORY:DELETE")
    public R<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return R.success(null, "删除分类成功");
    }
}
