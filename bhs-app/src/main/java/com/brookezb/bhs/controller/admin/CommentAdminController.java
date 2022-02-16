package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brooke_zb
 */
@RestController
@RequestMapping("/admin/comment")
@AllArgsConstructor
public class CommentAdminController {
    private CommentService commentService;
}
