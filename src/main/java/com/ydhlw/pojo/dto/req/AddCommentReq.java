package com.ydhlw.pojo.dto.req;

import lombok.Data;

@Data
public class AddCommentReq {
    private Integer eventId;
    private Integer userId;
    private String commentText;
}
