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
public class ListUsersRegistrationsResp {
    private Integer eventId;
    private String title;
    private String picUrl;
    private String location;
    private LocalDateTime eventTime;
    private String status;
    private LocalDateTime registeredAt;
}
