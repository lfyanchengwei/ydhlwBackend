package com.ydhlw.pojo.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEventCommentResp {
    private Integer commentId;
    private Integer userId;
    private String username;
    private String commentText;
    private LocalDateTime commentedAt;
}
