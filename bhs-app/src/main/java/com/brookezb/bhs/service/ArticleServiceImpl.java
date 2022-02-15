package com.brookezb.bhs.service;

import com.brookezb.bhs.entity.ArticleTimeline;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.ArticleMapper;
import com.brookezb.bhs.mapper.CategoryMapper;
import com.brookezb.bhs.mapper.TagMapper;
import com.brookezb.bhs.model.Article;
import com.brookezb.bhs.model.Tag;
import com.brookezb.bhs.utils.IdUtils;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {
    private ArticleMapper articleMapper;
    private CategoryMapper categoryMapper;
    private TagMapper tagMapper;

    @Cacheable(key = "#aid")
    @Override
    public Article selectById(Long aid) {
        Article article = articleMapper.selectById(aid);
        if (article == null) {
            throw new NotFoundException("文章不存在");
        }
        return article;
    }

    @Override
    public List<Article> selectAll(Article.Status status) {
        return articleMapper.selectAllByIdList(articleMapper.selectAll(status));
    }

    @Override
    public List<Article> selectAllByUserId(Long uid, Article.Status status) {
        return articleMapper.selectAllByIdList(articleMapper.selectAllByUserId(uid, status));
    }

    @Override
    public List<Article> selectAllByCategoryId(Long cid, Article.Status status) {
        return articleMapper.selectAllByIdList(articleMapper.selectAllByCategoryId(cid, status));
    }

    @Override
    public List<Article> selectAllByTagName(String tag, Article.Status status) {
        return articleMapper.selectAllByIdList(articleMapper.selectAllByTagName(tag, status));
    }

    @Override
    public List<ArticleTimeline> selectAllTimeline() {
        return articleMapper.selectAllTimeline();
    }

    @Override
    @Transactional
    public void insert(Article article) {
        // 查询分类是否存在
        if (article.getCategory() == null || categoryMapper.verifyCategory(article.getCategory().getCid()) == null) {
            throw new InvalidException("文章分类不存在");
        }

        // 插入id
        article.setAid(IdUtils.nextId());

        // 插入标签
        tagMapper.insertList(article.getTags());
        List<Tag> tags = tagMapper.selectAllByList(article.getTags());
        tagMapper.insertRelationByAid(article.getAid(), tags);

        articleMapper.insert(article);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#article.aid")
    public void update(Article article) {
        if (articleMapper.verifyArticle(article.getAid()) == null) {
            throw new NotFoundException("文章不存在");
        }
        articleMapper.update(article);

        // 更新标签
        tagMapper.insertList(article.getTags());
        articleMapper.deleteTagsNotInList(article.getAid(), article.getTags());
        articleMapper.insertTags(article.getAid(), tagMapper.selectAllByList(article.getTags()));
    }

    @Override
    @Transactional
    @CacheEvict(key = "#aid")
    public void delete(Long aid) {
        if (articleMapper.verifyArticle(aid) == null) {
            throw new NotFoundException("文章不存在");
        }
        articleMapper.delete(aid);
    }
}
