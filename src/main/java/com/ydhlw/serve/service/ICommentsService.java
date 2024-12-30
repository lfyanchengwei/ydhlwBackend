package com.ydhlw.serve.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ydhlw.pojo.dao.entity.Comments;
import com.ydhlw.pojo.dto.req.AddCommentReq;
import com.ydhlw.pojo.dto.resp.ListEventCommentResp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public interface ICommentsService extends IService<Comments> {
    //用户增加评论
    void addComment(AddCommentReq req);
    //获取活动评论
    List<ListEventCommentResp> getEventComments(Integer eventId);
    //根据活动id删除所有评论
    void deleteCommentsByEventId(Integer eventId);
}
