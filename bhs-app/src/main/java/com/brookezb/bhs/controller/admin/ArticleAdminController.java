package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.constant.RegexConstants;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/admin/article")
@AllArgsConstructor
public class ArticleAdminController {
    private ArticleService articleService;

    @GetMapping("/{id:\\d+}")
    @RequirePermission({"ARTICLE:READONLY", "ARTICLE:FULLACCESS"})
    public R<?> getArticle(@PathVariable Long id) {
        return R.success(articleService.selectById(id));
    }

    @GetMapping("")
    @RequirePermission({"ARTICLE:READONLY", "ARTICLE:FULLACCESS"})
    public R<?> getArticleList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") int page,
            @RequestParam(defaultValue = "10") @Pattern(regexp = RegexConstants.PAGE, message = "页数需为10/20/30") String size,
            @RequestParam(required = false) Long uid,
            @RequestParam(required = false) Long cid,
            @RequestParam(required = false) Long tid,
            @RequestParam(defaultValue = "PUBLISHED") Article.Status status
            ) {
        PageHelper.startPage(page, Integer.parseInt(size));
        return R.success(
                PageInfo.of(articleService.selectAll(uid, cid, tid, status))
        );
    }

    @PostMapping("")
    @RequirePermission("ARTICLE:FULLACCESS")
    public R<?> addArticle(@RequestBody @Validated(Article.Add.class) Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        User user = new User();
        user.setUid(uid);
        article.setUser(user);
        articleService.insert(article);
        return R.success(null, "文章发布成功");
    }

    @PutMapping("")
    @RequirePermission("ARTICLE:FULLACCESS")
    public R<?> updateArticle(@RequestBody @Validated(Article.Update.class) Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        User user = new User();
        user.setUid(uid);
        article.setUser(user);
        articleService.update(article);
        return R.success(null, "文章更新成功");
    }

    @DeleteMapping("/{id:\\d+}")
    @RequirePermission("ARTICLE:FULLACCESS")
    public R<?> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        return R.success(null, "文章删除成功");
    }
}
