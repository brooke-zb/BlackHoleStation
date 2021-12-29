package top.brookezb.bhs.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.model.Article;
import top.brookezb.bhs.service.ArticleService;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("")
    public R<?> insert(@RequestBody Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        article.setUid(uid);
        articleService.insert(article);
        return R.success(null, "文章发布成功");
    }

    @PutMapping("")
    public R<?> update(@RequestBody Article article, @SessionAttribute(AppConstants.SESSION_USER_KEY) Long uid) {
        article.setUid(uid);
        articleService.update(article);
        return R.success(null, "文章更新成功");
    }
}
