package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.mapper.TestMapper;
import top.brookezb.bhs.model.TestEntity;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    TestMapper mapper;

    @Override
    public TestEntity getTest(Integer tid) {
        return mapper.getTest(tid);
    }

    @Override
    public List<TestEntity> getTestList() {
        return mapper.getTestList();
    }

    @Override
    public Integer insertTest(TestEntity test) {
        return mapper.insertTest(test);
    }

    @Override
    public Integer updateTest(TestEntity test) {
        return mapper.updateTest(test);
    }

    @Override
    public Integer deleteTest(Integer tid) {
        return mapper.deleteTest(tid);
    }
}
