package top.brookezb.bhs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.brookezb.bhs.annotation.PermitAll;
import top.brookezb.bhs.annotation.RequireAuth;

/**
 * @author brooke_zb
 */
@RestController
public class TestController {

    @RequireAuth
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

}
