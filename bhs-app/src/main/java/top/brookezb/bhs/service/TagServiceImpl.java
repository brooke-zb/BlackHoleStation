package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
        return tagMapper.selectById(tid);
    }

    @Override
    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    @Override
    public boolean insert(Tag tag) {
        return tagMapper.insert(tag) > 0;
    }

    @Override
    public boolean update(Tag tag) {
        return tagMapper.update(tag) > 0;
    }

    @Override
    public boolean delete(Long tid) {
        return tagMapper.delete(tid) > 0;
    }

    @Override
    public boolean deleteList(List<Long> tids) {
        return tagMapper.deleteList(tids) > 0;
    }
}
