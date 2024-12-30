package com.ydhlw.pojo.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditEventReq {
    public Integer eventId;
    public String title;
    public String description;
    public String location;
    public LocalDateTime eventDate;
    public Integer capacity;
    public String picUrl;
    public LocalDateTime registrationDeadline;
    public String createdBy;
}
