package top.brookezb.bhs.service;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.exception.InvalidException;
import top.brookezb.bhs.exception.NotFoundException;
import top.brookezb.bhs.mapper.ArticleMapper;
import top.brookezb.bhs.model.Article;

import java.util.List;

/**
 * @author brooke_zb
 */
@CacheConfig(cacheNames = "article")
@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;

    @Cacheable(key = "#id")
    @Override
    public Article selectById(Long id, boolean compareTime, boolean enabled) {
        Article article = articleMapper.selectById(id, compareTime, enabled);
        if (article == null) {
            throw new NotFoundException("文章不存在");
        }
        return article;
    }

    @Override
    public PageInfo<List<Article>> getArticles(int page, int size, String title, boolean enabled) {
        return null;
    }

    @Override
    public void insert(Article article) {
        if (articleMapper.insert(article) < 1) {
            throw new InvalidException("文章插入失败");
        }
    }

    @CacheEvict(key = "#article.aid")
    @Override
    public void update(Article article) {
        if (articleMapper.update(article) < 1) {
            throw new InvalidException("文章更新失败");
        }
    }

    @CacheEvict(key = "#id")
    @Override
    public void delete(Long id) {
        if (articleMapper.delete(id) < 1) {
            throw new InvalidException("文章删除失败");
        }
    }
}
