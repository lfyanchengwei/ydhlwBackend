package com.ydhlw.pojo.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEventReq {
    private Integer eventId;
    private Integer userId;
    private String registrationText;
}
