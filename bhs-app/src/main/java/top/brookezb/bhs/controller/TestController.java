package top.brookezb.bhs.controller;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.model.TestEntity;
import top.brookezb.bhs.service.TestService;

/**
 * @author brooke_zb
 */
@CacheConfig(cacheNames = "test")
@RestController
@AllArgsConstructor
public class TestController {
    TestService testService;

    @Cacheable(key = "'test:' + #id", unless = "!#result.success")
    @GetMapping("/test/{id:\\d+}")
    public R<?> test(@PathVariable Integer id) {
        TestEntity testEntity = testService.getTest(id);
        if (testEntity == null) {
            return R.fail("没有找到该测试值");
        }
        return R.success(testEntity);
    }

    @CacheEvict(key = "'test:' + #id")
    @GetMapping("/test2/{id:\\d+}")
    public R<?> test2(@PathVariable Integer id) {
        TestEntity testEntity = testService.getTest(id);
        testEntity.setTest(testEntity.getTest() + "1");
        if (testService.updateTest(testEntity) > 0) {
            return R.success();
        }
        return R.fail();
    }

}
