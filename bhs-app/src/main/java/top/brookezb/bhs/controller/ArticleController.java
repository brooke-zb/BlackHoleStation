package top.brookezb.bhs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
