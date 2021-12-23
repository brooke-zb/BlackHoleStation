package top.brookezb.bhs.service;

import top.brookezb.bhs.model.TestEntity;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface TestService {
    TestEntity getTest(Integer tid);

    List<TestEntity> getTestList();

    Integer insertTest(TestEntity test);

    Integer updateTest(TestEntity test);

    Integer deleteTest(Integer tid);
}
