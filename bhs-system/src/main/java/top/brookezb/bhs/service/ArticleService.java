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
     * @return 文章
     */
    Article selectById(Long id, boolean compareTime, boolean enabled);

    /**
     * 获取文章列表
     *
     * @return 文章列表
     */
    PageInfo<List<Article>> getArticles(int page, int size, String title, boolean enabled);

    /**
     * 新增文章
     *
     * @param article 文章
     */
    boolean insert(Article article);

    /**
     * 更新文章
     * @param article 文章
     * @return 更新结果
     */
    boolean update(Article article);

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除结果
     */
    boolean delete(Long id);
}
