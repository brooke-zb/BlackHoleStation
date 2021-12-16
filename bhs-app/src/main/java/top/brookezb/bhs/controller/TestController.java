package top.brookezb.bhs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brooke_zb
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
