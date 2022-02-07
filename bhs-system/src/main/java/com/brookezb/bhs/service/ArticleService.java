package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Article;

import java.util.List;

/**
 * 文章服务
 *
 * @author brooke_zb
 */
public interface ArticleService {
    /**
     * 根据文章id获取文章
     *
     * @param aid 文章id
     * @return 文章
     */
    Article selectById(Long aid);

    /**
     * 获取文章列表
     * @param uid 用户id
     * @param cid 分类id
     * @param status 文章状态
     * @return 文章列表
     */
    List<Article> getArticles(Long uid, Long cid, Article.Status status);

    /**
     * 新增文章
     *
     * @param article 文章
     */
    void insert(Article article);

    /**
     * 更新文章
     * @param article 文章
     */
    void update(Article article);

    /**
     * 删除文章
     * @param aid 文章id
     */
    void delete(Long aid);
}
