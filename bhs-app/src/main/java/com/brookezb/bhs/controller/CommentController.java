package com.brookezb.bhs.controller;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Comment;
import com.brookezb.bhs.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @GetMapping("/{id:\\d+}")
    public R<?> getComment(@PathVariable Long id) {
        return R.success(commentService.selectById(id, false));
    }

    @GetMapping("")
    public R<?> getCommentList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") int page,
            @RequestParam Long aid
    ) {
        PageHelper.startPage(page, AppConstants.DEFAULT_PAGE_SIZE);
        return R.success(
                PageInfo.of(commentService.selectAllByArticleId(aid))
        );
    }

    @PostMapping("")
    public R<?> addComment(
            @RequestBody @Validated(Comment.Add.class) Comment comment,
            @SessionAttribute(value = AppConstants.SESSION_USER_KEY, required = false) Long uid
    ) {
        if (commentService.insert(comment, uid == null)) {
            return R.success(null, "评论成功");
        }
        return R.success(null, "评论成功，请等待审核");
    }
}
