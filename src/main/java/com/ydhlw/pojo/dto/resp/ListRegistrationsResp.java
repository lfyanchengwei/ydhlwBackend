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
public class ListRegistrationsResp {
    private Integer registrationId;
    private Integer userId;
    private String username;
    private Integer eventId;
    private String title;
    private String registrationText;
    private String status;
    private LocalDateTime registeredAt;
}
