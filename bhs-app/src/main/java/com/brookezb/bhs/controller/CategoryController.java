package com.brookezb.bhs.controller;

import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping("/{id:\\d+}")
    public R<?> getCategory(@PathVariable Long id) {
        return R.success(categoryService.selectById(id));
    }

    @GetMapping("")
    public R<?> getCategoryList() {
        return R.success(categoryService.selectAll());
    }
}
