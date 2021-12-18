package top.brookezb.bhs.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.brookezb.bhs.entity.R;
import top.brookezb.bhs.entity.TestEntity;
import top.brookezb.bhs.service.TestService;

/**
 * @author brooke_zb
 */
@RestController
@AllArgsConstructor
public class TestController {
    TestService testService;

    @GetMapping("/test/{id:\\d+}")
    public R<?> test(@PathVariable Integer id) {
        TestEntity testEntity = testService.getTest(id);
        if (testEntity == null) {
            return R.fail("没有找到该测试值");
        }
        return R.success(testEntity);
    }

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
