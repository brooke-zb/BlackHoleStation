package com.brookezb.bhs.task;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.service.ArticleService;
import com.brookezb.bhs.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author brooke_zb
 */
@Slf4j
@Component
@AllArgsConstructor
public class CountArticleViewsTask {
    private RedisUtils redisUtils;
    private ArticleService articleService;
    private int count = 0;

    @Scheduled(cron = "${task.cron.count-article-views:@daily}")
    public void countArticleViews() {
        log.info("开始统计文章阅读量");

        this.count = 0;
        redisUtils.getKeys(AppConstants.REDIS_ARTICLE_VIEW_COUNT + "*")
                .forEach(key -> {
                    Integer read = redisUtils.getSetSize(key);
                    Long aid = Long.valueOf(key.substring(AppConstants.REDIS_ARTICLE_VIEW_COUNT.length()));
                    if (articleService.updateViews(aid, read)) {
                        log.info("- 文章阅读量持久化成功，文章ID={}, 阅读增量={}", aid, read);
                    }
                    redisUtils.delete(key);
                    count++;
                });

        log.info("完成统计，共更新了{}篇文章", count);
    }
}
