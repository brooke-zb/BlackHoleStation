package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.mapper.TagMapper;
import top.brookezb.bhs.model.Tag;

import java.util.List;

/**
 * @author brooke_zb
 */
@CacheConfig(cacheNames = "tag")
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;

    @Override
    @Cacheable(key = "#tid")
    public Tag selectById(Long tid) {
        return tagMapper.selectById(tid);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    @Override
    @CacheEvict(key = "'all'")
    public boolean insert(Tag tag) {
        return tagMapper.insert(tag) > 0;
    }

    @Override
    @CacheEvict(key = "#tag.tid")
    public boolean update(Tag tag) {
        return tagMapper.update(tag) > 0;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "#tid")
    })
    public boolean delete(Long tid) {
        return tagMapper.delete(tid) > 0;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean deleteList(List<Long> tids) {
        return tagMapper.deleteList(tids) > 0;
    }
}
