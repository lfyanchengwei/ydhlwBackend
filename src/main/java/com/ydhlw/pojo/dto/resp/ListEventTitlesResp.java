package com.ydhlw.pojo.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEventTitlesResp {
    private Integer eventId;
    private String title;
}
