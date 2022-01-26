package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.brookezb.bhs.exception.InvalidException;
import top.brookezb.bhs.exception.NotFoundException;
import top.brookezb.bhs.mapper.TagMapper;
import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;

    @Override
    public Tag selectById(Long tid) {
        Tag tag = tagMapper.selectById(tid);
        if (tag != null) {
            return tag;
        }
        throw new NotFoundException("未找到该标签");
    }

    @Override
    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    @Override
    @Transactional
    public void insert(Tag tag) {
        if (tagMapper.insert(tag) > 0) {
            return;
        }
        if (tagMapper.selectByName(tag.getName()) != null) {
            throw new InvalidException("该标签已存在");
        }
        throw new InvalidException("标签插入失败");
    }

    @Override
    @Transactional
    public void update(Tag tag) {
        if (tagMapper.selectById(tag.getTid()) != null) {
            tagMapper.update(tag);
            return;
        }
        throw new NotFoundException("未找到该标签");
    }

    @Override
    @Transactional
    public void delete(Long tid) {
        tagMapper.delete(tid);
    }

    @Override
    public void deleteList(List<Long> tids) {
        tagMapper.deleteList(tids);
    }
}
