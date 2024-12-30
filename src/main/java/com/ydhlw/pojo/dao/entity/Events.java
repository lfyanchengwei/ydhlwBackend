package com.ydhlw.pojo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("events")
@Builder
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "event_id", type = IdType.AUTO)
    private Integer eventId;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动照片
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 活动地点
     */
    private String location;

    /**
     * 活动日期
     */
    @TableField("event_date")
    private LocalDateTime eventDate;

    /**
     * 活动最多人数
     */
    private Integer capacity;

    /**
     * 报名截止日期
     */
    @TableField("registration_deadline")
    private LocalDateTime registrationDeadline;

    /**
     * 创建活动的管理员id
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;


}
