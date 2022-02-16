package com.brookezb.bhs.controller.admin;

import com.brookezb.bhs.annotation.RequirePermission;
import com.brookezb.bhs.constant.RegexConstants;
import com.brookezb.bhs.entity.R;
import com.brookezb.bhs.model.Comment;
import com.brookezb.bhs.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;

/**
 * @author brooke_zb
 */
@Validated
@RestController
@RequestMapping("/admin/comment")
@AllArgsConstructor
public class CommentAdminController {
    private CommentService commentService;

    @GetMapping("/{id:\\d+}")
    @RequirePermission("COMMENT:GET")
    public R<?> getComment(@PathVariable Long id) {
        return R.success(commentService.selectById(id, true));
    }

    @GetMapping("")
    @RequirePermission("COMMENT:GET")
    public R<?> getCommentList(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页数不能小于1") int page,
            @RequestParam(defaultValue = "10") @Pattern(regexp = RegexConstants.PAGE, message = "页数需为10/20/30") String size,
            @RequestParam(required = false) Long aid,
            @RequestParam(required = false) String ip,
            @RequestParam(defaultValue = "PUBLISHED") Comment.Status status
    ) {
        PageHelper.startPage(page, Integer.parseInt(size));
        return R.success(
                PageInfo.of(commentService.selectAll(aid, ip, status))
        );
    }

    @PutMapping("")
    @RequirePermission("COMMENT:UPDATE")
    public R<?> updateComment(@RequestBody @Validated(Comment.Update.class) Comment comment) {
        commentService.update(comment);
        return R.success(null, "更新评论成功");
    }

    @PatchMapping("/{id:\\d+}/status/{status:PUBLISHED|PENDING|INVISIBLE}")
    @RequirePermission("COMMENT:UPDATE")
    public R<?> updateCommentStatus(@PathVariable Long id, @PathVariable Comment.Status status) {
        commentService.updateStatus(id, status);
        return R.success(null, "更新评论状态成功");
    }

    @PatchMapping("/{ids:\\d+(?:,\\d+)+}/status/{status:PUBLISHED|PENDING|INVISIBLE}")
    @RequirePermission("COMMENT:UPDATE")
    public R<?> updateCommentStatusList(@PathVariable String ids, @PathVariable Comment.Status status) {
        List<Long> list = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        commentService.updateStatusList(list, status);
        return R.success(null, "更新评论状态成功");
    }

    @DeleteMapping("/{id:\\d+}")
    @RequirePermission("COMMENT:DELETE")
    public R<?> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return R.success(null, "删除评论成功");
    }

    @DeleteMapping("/{ids:\\d+(?:,\\d+)+}")
    @RequirePermission("COMMENT:DELETE")
    public R<?> deleteCommentList(@PathVariable String ids) {
        List<Long> list = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        commentService.deleteList(list);
        return R.success(null, "删除评论成功");
    }
}
