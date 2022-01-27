package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.brookezb.bhs.exception.InvalidException;
import top.brookezb.bhs.exception.NotFoundException;
import top.brookezb.bhs.mapper.ArticleMapper;
import top.brookezb.bhs.mapper.CategoryMapper;
import top.brookezb.bhs.model.Category;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;
    private ArticleMapper articleMapper;

    @Override
    public Category selectById(Long cid) {
        Category category = categoryMapper.selectById(cid);
        if (category != null) {
            return category;
        }
        throw new NotFoundException("未找到该分类");
    }

    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    @Override
    @Transactional
    public void insert(Category category) {
        if (categoryMapper.selectByName(category.getName()) != null) {
            throw new InvalidException("该分类已存在");
        }
        Category parent = categoryMapper.selectById(category.getParent());
        if (parent == null) {
            throw new InvalidException("父分类不存在");
        }
        if (parent.getParent() != null) {
            throw new InvalidException("父分类不是顶级分类");
        }
        if (categoryMapper.insert(category) > 0) {
            return;
        }
        throw new InvalidException("分类插入失败");
    }

    @Override
    @Transactional
    public void update(Category category) {
        Category old = categoryMapper.selectById(category.getCid());

        // 检查有无该分类
        if (old == null) {
            throw new NotFoundException("未找到该分类");
        }

        // 检查该分类是否为顶级分类，是则不能修改父分类
        if (old.getChildren().size() > 0 && category.getParent() != null) {
            throw new InvalidException("该分类为顶级分类，无法修改父分类");
        }
        categoryMapper.update(category);
    }

    @Override
    @Transactional
    public void delete(Long cid) {
        // 检查该分类下是否有文章
        if (articleMapper.selectCountByCategoryId(cid) > 0) {
            throw new InvalidException("该分类下有文章，无法删除");
        }

        Category category = categoryMapper.selectById(cid);
        if (category != null) {
            categoryMapper.removeParentByCid(cid);
            categoryMapper.delete(cid);
            return;
        }
        throw new NotFoundException("未找到该分类");
    }
}
