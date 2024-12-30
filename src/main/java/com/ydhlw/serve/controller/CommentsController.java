package com.ydhlw.serve.controller;


import com.ydhlw.common.result.Result;
import com.ydhlw.pojo.dto.req.AddCommentReq;
import com.ydhlw.serve.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    @Autowired
    private ICommentsService commentsService;
    @PostMapping("/addComment")
    public Result<String> addComment(@RequestBody AddCommentReq req) {
        try{
            commentsService.addComment(req);
            return Result.success("评论成功");
        }catch(Exception e){
            return Result.error("评论失败: " + e.getMessage());
        }
    }
    @GetMapping("/getEventComments")
    public Result<Object> getEventComments(@RequestParam("eventId") Integer eventId) {
        try{
            return Result.success(commentsService.getEventComments(eventId));
        }catch(Exception e){
            return Result.error("获取评论失败: " + e.getMessage());
        }
    }

}
