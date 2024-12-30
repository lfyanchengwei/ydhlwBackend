package com.ydhlw.pojo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报名表
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报名id
     */
    @TableId(value = "registration_id", type = IdType.AUTO)
    private Integer registrationId;

    /**
     * 报名用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 报名活动id
     */
    @TableField("event_id")
    private Integer eventId;

    /**
     * 报名文本
     */
    @TableField("registration_text")
    private String registrationText;

    /**
     * 报名状态
     */
    private String status;

    /**
     * 报名时间
     */
    private LocalDateTime registeredAt;


}
