package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/admin/article")
@AllArgsConstructor
public class ArticleAdminController {
    private ArticleService articleService;

    @PostMapping("")
    @RequirePermission("ARTICLE:ADD")
    public R<?> insert(@RequestBody Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        User user = new User();
        user.setUid(uid);
        article.setUser(user);
        articleService.insert(article);
        return R.success(null, "文章发布成功");
    }

    @PutMapping("")
    @RequirePermission("ARTICLE:UPDATE")
    public R<?> update(@RequestBody Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        User user = new User();
        user.setUid(uid);
        article.setUser(user);
        articleService.update(article);
        return R.success(null, "文章更新成功");
    }
}
