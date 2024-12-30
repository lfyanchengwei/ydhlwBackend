package com.ydhlw.serve.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydhlw.pojo.dao.entity.Events;
import com.ydhlw.pojo.dao.entity.Registration;
import com.ydhlw.pojo.dao.entity.Users;
import com.ydhlw.pojo.dao.mapper.RegistrationMapper;
import com.ydhlw.pojo.dto.req.RegisterEventReq;
import com.ydhlw.pojo.dto.resp.ListPassedRegistrationsResp;
import com.ydhlw.pojo.dto.resp.ListRegistrationsResp;
import com.ydhlw.pojo.dto.resp.ListUsersParticipationResp;
import com.ydhlw.pojo.dto.resp.ListUsersRegistrationsResp;
import com.ydhlw.serve.service.IRegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydhlw.serve.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 报名表 服务实现类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements IRegistrationService {
    @Autowired
    private RegistrationMapper registrationMapper;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private EventsServiceImpl eventsService;

    //用户报名
    @Override
    public void registerEvent(RegisterEventReq req) {
        Registration registration = Registration.builder()
                .userId(req.getUserId())
                .eventId(req.getEventId())
                .registrationText(req.getRegistrationText())
                .status("pending")
                .registeredAt(LocalDateTime.now())
                .build();
        save(registration);
    }

    //获取报名列表
    @Override
    public IPage<ListRegistrationsResp> listRegistrations(Integer eventId, Integer pageNum, Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);
        IPage<Registration> result;
        if (eventId != null) {
            // 如果 eventId 存在，筛选指定活动的报名信息
            QueryWrapper<Registration> wrapper = new QueryWrapper<>();
            wrapper.eq("event_id", eventId);
            result = this.baseMapper.selectPage(page, wrapper);
        } else {
            // 如果 eventId 不存在，返回所有报名信息
            result = this.baseMapper.selectPage(page, null);
        }
        // 获取所有 userId 和 eventId
        List<Registration> registrations = result.getRecords();
        List<Integer> userIds = registrations.stream()
                .map(Registration::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<Integer> eventIds = registrations.stream()
                .map(Registration::getEventId)
                .distinct()
                .collect(Collectors.toList());


        // 批量查询用户名和活动标题
        Map<Integer, String> userMap = usersService.getUsernamesByIds(userIds);
        Map<Integer, String> eventMap = eventsService.getTitlesByIds(eventIds);

        // 构建返回结果
        IPage<ListRegistrationsResp> resultPage = new Page<>();
        resultPage.setCurrent(result.getCurrent());
        resultPage.setSize(result.getSize());
        resultPage.setTotal(result.getTotal());
        resultPage.setRecords(result.getRecords().stream()
                .map(event -> {
                    ListRegistrationsResp resp = new ListRegistrationsResp();
                    resp.setRegistrationId(event.getRegistrationId());
                    resp.setUserId(event.getUserId());
                    resp.setUsername(userMap.get(event.getUserId()));
                    resp.setEventId(event.getEventId());
                    resp.setTitle(eventMap.get(event.getEventId()));
                    resp.setRegistrationText(event.getRegistrationText());
                    resp.setStatus(event.getStatus());
                    resp.setRegisteredAt(event.getRegisteredAt());
                    return resp;
                })
                .collect(Collectors.toList()));
        return resultPage;
    }

    @Override
    public String getUserRegistrationStatus(Integer eventId, Integer userId){
        Registration registration = registrationMapper.selectOne(
                new QueryWrapper<Registration>().eq("event_id", eventId).eq("user_id", userId)
        );
        if (registration != null) {
            return registration.getStatus();
        } else {
            return "notReg";
        }
    }

    //审核报名
    @Override
    public void updateRegistrationStatus(Integer registrationId, String status) {
        Registration registration = getById(registrationId);
        if (registration != null) { // 确保 registration 不为 null
            registration.setStatus(status);
            UpdateWrapper<Registration> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("registration_id", registrationId);
            registrationMapper.update(registration, updateWrapper); // 使用 UpdateWrapper
        } else {
            throw new RuntimeException("Registration not found with ID: " + registrationId);
        }
    }

    @Override
    public IPage<ListPassedRegistrationsResp> listPassedRegistrations(Integer eventId, Integer pageNum, Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);

        QueryWrapper<Registration> wrapper = new QueryWrapper<>();
        wrapper.eq("event_id", eventId).in("status", Arrays.asList("approved", "participated"));
        IPage<Registration> result = this.baseMapper.selectPage(page, wrapper);

        IPage<ListPassedRegistrationsResp> resultPage = new Page<>();
        resultPage.setCurrent(result.getCurrent());
        resultPage.setSize(result.getSize());
        resultPage.setTotal(result.getTotal());
        resultPage.setRecords(result.getRecords().stream()
                .map(event -> {
                    ListPassedRegistrationsResp resp = new ListPassedRegistrationsResp();
                    resp.setRegistrationId(event.getRegistrationId());
                    resp.setUserId(event.getUserId());
                    resp.setUsername(usersService.userFindById(event.getUserId()).getUserName());
                    resp.setPhone(usersService.userFindById(event.getUserId()).getPhone());
                    resp.setEmail(usersService.userFindById(event.getUserId()).getEmail());
                    resp.setStatus(event.getStatus());
                    return resp;
                })
                .collect(Collectors.toList()));
        return resultPage;
    }

    //根据活动id删除所有活动报名信息
    @Override
    public void deleteRegistrationByEventId(Integer eventId) {
        QueryWrapper<Registration> wrapper = new QueryWrapper<>();
        wrapper.eq("event_id", eventId);
        this.baseMapper.delete(wrapper);
    }

    //分页获得指定活动的待审核报名列表
    public IPage<ListRegistrationsResp> listPendingRegistrations(Integer eventId, Integer pageNum, Integer pageSize){
        Page page = new Page<>(pageNum, pageSize);
        IPage<Registration> result;
        if (eventId != null) {
            // 如果 eventId 存在，筛选指定活动的报名信息
            QueryWrapper<Registration> wrapper = new QueryWrapper<>();
            wrapper.eq("event_id", eventId).eq("status", "pending");
            result = this.baseMapper.selectPage(page, wrapper);
        } else {
            // 如果 eventId 不存在，返回所有报名信息
            QueryWrapper<Registration> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "pending");
            result = this.baseMapper.selectPage(page, wrapper);
        }
        // 获取所有 userId 和 eventId
        List<Registration> registrations = result.getRecords();
        List<Integer> userIds = registrations.stream()
                .map(Registration::getUserId)
                .distinct()
                .collect(Collectors.toList());
        List<Integer> eventIds = registrations.stream()
                .map(Registration::getEventId)
                .distinct()
                .collect(Collectors.toList());


        // 批量查询用户名和活动标题
        Map<Integer, String> userMap = usersService.getUsernamesByIds(userIds);
        Map<Integer, String> eventMap = eventsService.getTitlesByIds(eventIds);

        // 构建返回结果
        IPage<ListRegistrationsResp> resultPage = new Page<>();
        resultPage.setCurrent(result.getCurrent());
        resultPage.setSize(result.getSize());
        resultPage.setTotal(result.getTotal());
        resultPage.setRecords(result.getRecords().stream()
                .map(event -> {
                    ListRegistrationsResp resp = new ListRegistrationsResp();
                    resp.setRegistrationId(event.getRegistrationId());
                    resp.setUserId(event.getUserId());
                    resp.setUsername(userMap.get(event.getUserId()));
                    resp.setEventId(event.getEventId());
                    resp.setTitle(eventMap.get(event.getEventId()));
                    resp.setRegistrationText(event.getRegistrationText());
                    resp.setStatus(event.getStatus());
                    resp.setRegisteredAt(event.getRegisteredAt());
                    return resp;
                })
                .collect(Collectors.toList()));
        return resultPage;
    }

    //用户获得自己参加过的所有活动
    @Override
    public List<ListUsersParticipationResp> listUsersParticipation(Integer userId) {
        QueryWrapper<Registration> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("status", "participated");
        List<Registration> registrations = this.baseMapper.selectList(wrapper);
        // 获取所有 eventId
        List<Integer> eventIds = registrations.stream()
                .map(Registration::getEventId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, Events> eventMap = eventsService.getInfosByIds(eventIds);
        return registrations.stream()
                .map(reg -> ListUsersParticipationResp.builder()
                        .eventId(reg.getEventId())
                        .picUrl(eventMap.get(reg.getEventId()).getPicUrl())
                        .title(eventMap.get(reg.getEventId()).getTitle())
                        .location(eventMap.get(reg.getEventId()).getLocation())
                        .eventTime(eventMap.get(reg.getEventId()).getEventDate())
                        .build())
                .collect(Collectors.toList());
    }

    //用户获得自己报名的活动列表
    @Override
    public List<ListUsersRegistrationsResp> listUsersRegistrations(Integer userId) {
        QueryWrapper<Registration> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).ne("status", "participated");
        List<Registration> registrations = this.baseMapper.selectList(wrapper);
        return registrations.stream()
                .map(reg -> ListUsersRegistrationsResp.builder()
                        .eventId(reg.getEventId())
                        .title(eventsService.getEventById(reg.getEventId()).getTitle())
                        .location(eventsService.getEventById(reg.getEventId()).getLocation())
                        .eventTime(eventsService.getEventById(reg.getEventId()).getEventDate())
                        .picUrl(eventsService.getEventById(reg.getEventId()).getPicUrl())
                        .status(reg.getStatus())
                        .registeredAt(reg.getRegisteredAt())
                        .build())
                .collect(Collectors.toList());
    }

}
