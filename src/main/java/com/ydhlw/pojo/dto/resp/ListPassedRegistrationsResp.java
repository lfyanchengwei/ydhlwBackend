package com.ydhlw.pojo.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPassedRegistrationsResp {
    private Integer registrationId;
    private Integer userId;
    private String username;
    private String phone;
    private String email;
    private String status;
}
