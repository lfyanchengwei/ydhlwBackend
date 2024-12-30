package com.ydhlw.serve.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydhlw.pojo.dao.entity.Comments;
import com.ydhlw.pojo.dao.entity.Registration;
import com.ydhlw.pojo.dao.entity.Users;
import com.ydhlw.pojo.dao.mapper.CommentsMapper;
import com.ydhlw.pojo.dao.mapper.RegistrationMapper;
import com.ydhlw.pojo.dto.req.AddCommentReq;
import com.ydhlw.pojo.dto.resp.ListEventCommentResp;
import com.ydhlw.serve.service.ICommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydhlw.serve.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private IUsersService usersService;

    @Override
    public void addComment(AddCommentReq req) {
        //评论不能为空
        if (req.getCommentText() == null || req.getCommentText().isEmpty()) {
            throw new RuntimeException("评论不能为空");
        }
        Comments comments = new Comments();
        comments.setCommentText(req.getCommentText());
        comments.setEventId(req.getEventId());
        comments.setUserId(req.getUserId());
        comments.setCommentDate(LocalDateTime.now());
        save(comments);
    }

    @Override
    public List<ListEventCommentResp> getEventComments(Integer eventId) {
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("event_id", eventId);
        List<Comments> comments = this.baseMapper.selectList(wrapper);
        // 获取所有 userId
        List<Integer> userIds = comments.stream()
                .map(Comments::getUserId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, String> userMap = usersService.getUsernamesByIds(userIds);
        return comments.stream()
                .map(com->ListEventCommentResp.builder()
                        .commentId(com.getCommentId())
                        .userId(com.getUserId())
                        .username(userMap.getOrDefault(com.getUserId(), "Unknown"))
                        .commentText(com.getCommentText())
                        .commentedAt(com.getCommentDate()).build())
                .collect(Collectors.toList());
    }

    public void deleteCommentsByEventId(Integer eventId){
        QueryWrapper<Comments> wrapper = new QueryWrapper<>();
        wrapper.eq("event_id", eventId);
        this.baseMapper.delete(wrapper);
    }

}
