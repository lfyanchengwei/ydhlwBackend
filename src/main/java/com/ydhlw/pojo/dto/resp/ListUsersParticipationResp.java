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
public class ListUserselfRegistrationsResp {
    private Integer eventId;
    private String picUrl;
    private String title;
    private String location;
    private LocalDateTime eventTime;
}