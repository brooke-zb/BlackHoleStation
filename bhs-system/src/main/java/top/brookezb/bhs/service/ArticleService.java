package top.brookezb.bhs.service;

import com.github.pagehelper.PageInfo;
import top.brookezb.bhs.model.Article;

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
     * @param id 文章id
     * @param chooseDelayed 是否选择未来发布的文章
     * @param published 是否只获取已发布的文章
     * @return 文章
     */
    Article selectById(Long id, boolean chooseDelayed, boolean published);

    /**
     * 获取文章列表
     *
     * @param page 页码
     * @param size 每页数量
     * @param title 搜索标题
     * @param published 是否只获取已发布的文章
     * @return 文章列表
     */
    PageInfo<List<Article>> getArticles(int page, int size, String title, boolean published);

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
     * @param id 文章id
     */
    void delete(Long id);
}
