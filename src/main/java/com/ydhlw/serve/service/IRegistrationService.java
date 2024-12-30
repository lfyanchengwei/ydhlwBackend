package com.ydhlw.serve.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydhlw.pojo.dao.entity.Registration;
import com.ydhlw.pojo.dto.req.RegisterEventReq;
import com.ydhlw.pojo.dto.resp.ListPassedRegistrationsResp;
import com.ydhlw.pojo.dto.resp.ListRegistrationsResp;
import com.ydhlw.pojo.dto.resp.ListUsersParticipationResp;
import com.ydhlw.pojo.dto.resp.ListUsersRegistrationsResp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 报名表 服务类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public interface IRegistrationService extends IService<Registration> {
    //用户报名
    void registerEvent(RegisterEventReq req);
    // 分页获取报名列表
    IPage<ListRegistrationsResp> listRegistrations(Integer eventId, Integer pageNum, Integer pageSize);
    //获取用户报名状态
    String getUserRegistrationStatus(Integer eventId, Integer userId);
    //审核报名
    void updateRegistrationStatus(Integer registrationId, String status);
    //分页获取指定活动的已通过报名列表
    IPage<ListPassedRegistrationsResp> listPassedRegistrations(Integer eventId, Integer pageNum, Integer pageSize);
    //分页获得制定活动的待审核报名列表
    IPage<ListRegistrationsResp> listPendingRegistrations(Integer eventId, Integer pageNum, Integer pageSize);
    //根据活动id删除相关报名信息
    void deleteRegistrationByEventId(Integer eventId);
    //用户获得自己参加过的所有活动
    List<ListUsersParticipationResp> listUsersParticipation(Integer userId);
    //用户获得自己已报名的活动
    List<ListUsersRegistrationsResp> listUsersRegistrations(Integer userId);
}
