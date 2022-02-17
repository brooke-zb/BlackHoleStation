package com.brookezb.bhs.controller;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.brookezb.bhs.entity.R;

import javax.validation.constraints.Min;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/{id:\\d+}")
    public R<?> getArticle(@PathVariable Long id) {
        Article article = articleService.selectById(id);
        if (article.getStatus() != Article.Status.PUBLISHED) {
            throw new NotFoundException("文章不存在或已被删除");
        }

        // 更新阅读次数
        Integer views = articleService.getAndIncreaseViews(id);
        if (views != null) {
            article.setViews(article.getViews() + views);
        }
        return R.success(article);
    }

    @GetMapping({
            "",
            "/user/{uid:\\d+}",
            "/category/{cid:\\d+}",
            "/tag/{tag}",
    })
    public R<?> getArticleList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") int page,
            @PathVariable(required = false) Long uid,
            @PathVariable(required = false) Long cid,
            @PathVariable(required = false) String tag
    ) {
        PageHelper.startPage(page, AppConstants.DEFAULT_PAGE_SIZE).setOrderBy("created desc");
        if (tag != null) {
            return R.success(new PageInfo<>(articleService.selectAllByTagName(tag, Article.Status.PUBLISHED)));
        }
        return R.success(
                PageInfo.of(articleService.selectAll(uid, cid, null, Article.Status.PUBLISHED))
        );
    }

    @GetMapping("/timeline")
    public R<?> getArticleTimeline(@RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") int page) {
        PageHelper.startPage(page, AppConstants.TIMELINE_SIZE);
        return R.success(
                PageInfo.of(articleService.selectAllTimeline())
        );
    }
}
