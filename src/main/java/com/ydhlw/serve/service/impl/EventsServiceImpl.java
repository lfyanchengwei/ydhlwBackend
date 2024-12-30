package com.ydhlw.serve.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydhlw.pojo.dao.entity.Events;
import com.ydhlw.pojo.dao.mapper.EventsMapper;
import com.ydhlw.pojo.dto.req.DeleteEventReq;
import com.ydhlw.pojo.dto.req.EditEventReq;
import com.ydhlw.pojo.dto.req.NewEventReq;
import com.ydhlw.pojo.dto.resp.ListEventResp;
import com.ydhlw.pojo.dto.resp.ListEventTitlesResp;
import com.ydhlw.serve.service.ICommentsService;
import com.ydhlw.serve.service.IEventsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydhlw.serve.service.IRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements IEventsService {
    @Autowired
    private EventsMapper eventMapper;
    @Lazy
    @Autowired
    private IRegistrationService registrationService;
    @Lazy
    @Autowired
    private ICommentsService commentsService;
    @Override
    public void newEvent(NewEventReq req) {
        // 验证输入数据
        if (req.getTitle() == null || req.getTitle().isEmpty()) {
            throw new IllegalArgumentException("活动标题不能为空");
        }
        if (req.getDescription() == null || req.getDescription().isEmpty()) {
            throw new IllegalArgumentException("活动描述不能为空");
        }
        if (req.getLocation() == null || req.getLocation().isEmpty()) {
            throw new IllegalArgumentException("活动地点不能为空");
        }
        if (req.getEventDate() == null) {
            throw new IllegalArgumentException("活动日期不能为空");
        }
        if (req.getCapacity() == null || req.getCapacity() <= 0) {
            throw new IllegalArgumentException("活动最多人数必须大于0");
        }

        Events event = Events.builder()
            .title(req.getTitle())
            .description(req.getDescription())
                .picUrl(req.getPicUrl())
            .location(req.getLocation())
            .eventDate(req.getEventDate())
            .capacity(req.getCapacity())
            .registrationDeadline(req.getRegistrationDeadline())
            .createdBy(req.getCreatedBy())
            .createdAt(LocalDateTime.now())
            .build();
        save(event);
    }

    @Override
    public void editEvent(EditEventReq req) {
        Events event = new Events();
        event.setEventId(req.getEventId());
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setEventDate(req.getEventDate());
        event.setLocation(req.getLocation());
        event.setRegistrationDeadline(req.getRegistrationDeadline());
        event.setCreatedBy(req.getCreatedBy());
        event.setPicUrl(req.getPicUrl());
        event.setCapacity(req.getCapacity());
        updateById(event);
    }
//    @Override
//    public List<ListEventResp> listEvent() {
//        // 查询所有活动信息
//        List<Events> eventsList = list(new QueryWrapper<>());
//
//        // 将 Events 对象转换为 ListEventResp 对象
//        return eventsList.stream()
//                .map(event -> {
//                    ListEventResp resp = new ListEventResp();
//                    BeanUtils.copyProperties(event, resp);
//                    return resp;
//                })
//                .collect(Collectors.toList());
//    }
    @Override
    public IPage<ListEventResp> listEvent(Integer pageNum, Integer pageSize) {
        // 创建分页对象
        Page<Events> page = new Page<>(pageNum, pageSize);

        // 查询分页结果
        IPage<Events> eventsPage = page(page, new QueryWrapper<>());

        // 将分页结果中的数据转换为目标返回类型
        IPage<ListEventResp> resultPage = new Page<>();
        resultPage.setCurrent(eventsPage.getCurrent());
        resultPage.setSize(eventsPage.getSize());
        resultPage.setTotal(eventsPage.getTotal());
        resultPage.setRecords(eventsPage.getRecords().stream()
                .map(event -> {
                    ListEventResp resp = new ListEventResp();
                    BeanUtils.copyProperties(event, resp);
                    return resp;
                })
                .collect(Collectors.toList()));

        return resultPage;
    }

    @Override
    public List<ListEventTitlesResp> listEventIds() {
        return eventMapper.selectList(new QueryWrapper<>()).stream()
                .map(event -> {
                    ListEventTitlesResp resp = new ListEventTitlesResp();
                    BeanUtils.copyProperties(event, resp);
                    return resp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void DeleteEvent(DeleteEventReq req) {
        // 删除活动信息，同时删除 活动报名信息和活动评论信息
        // 删除活动报名信息
        registrationService.deleteRegistrationByEventId(req.getEventId());
        // 删除活动评论信息
        commentsService.deleteCommentsByEventId(req.getEventId());
        // 删除活动信息
        removeById(req.getEventId());
    }

    @Override
    public Events getEventById(Integer id) {
        return getById(id);
    }
    @Override
    // 批量查询活动标题
    public Map<Integer, String> getTitlesByIds(List<Integer> eventIds) {
        List<Events> events = eventMapper.selectByIds(eventIds);
        return events.stream()
                .collect(Collectors.toMap(Events::getEventId, Events::getTitle, (a, b) -> a)); // 防止重复 key 报错
    }
    @Override
    // 批量查询活动内容
    public Map<Integer, Events> getInfosByIds(List<Integer> eventIds) {
        List<Events> events = eventMapper.selectByIds(eventIds);
        return events.stream()
                .collect(Collectors.toMap(Events::getEventId, event->event, (a, b) -> a)); // 防止重复 key 报错
    }
}
