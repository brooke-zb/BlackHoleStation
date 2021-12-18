package top.brookezb.bhs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.brookezb.bhs.entity.TestEntity;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface TestMapper {
    TestEntity getTest(Integer tid);

    List<TestEntity> getTestList();

    Integer insertTest(TestEntity test);

    Integer updateTest(TestEntity test);

    Integer deleteTest(Integer tid);
}
