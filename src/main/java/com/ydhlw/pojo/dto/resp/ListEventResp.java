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
public class ListEventResp {
    private Integer eventId;
    private String title;
    private String description;
    private String picUrl;
    private String location;
    private LocalDateTime eventDate;
    private Integer capacity;
    private LocalDateTime registrationDeadline;
    private String createdBy;
    private LocalDateTime createdAt;
}
