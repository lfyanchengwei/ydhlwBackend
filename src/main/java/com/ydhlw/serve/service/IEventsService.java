package com.ydhlw.serve.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydhlw.pojo.dao.entity.Events;
import com.ydhlw.pojo.dto.req.DeleteEventReq;
import com.ydhlw.pojo.dto.req.EditEventReq;
import com.ydhlw.pojo.dto.req.NewEventReq;
import com.ydhlw.pojo.dto.resp.ListEventResp;
import com.ydhlw.pojo.dto.resp.ListEventTitlesResp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public interface IEventsService extends IService<Events> {
    // 新增活动
    public void newEvent(NewEventReq req);
    //编辑活动
    public void editEvent(EditEventReq req);
//    // 获取活动列表
//    public List<ListEventResp> listEvent();
    // 分页获取活动列表
    public IPage<ListEventResp> listEvent(Integer pageNum, Integer pageSize);
    // 获取所有活动id和标题
    public List<ListEventTitlesResp> listEventIds();
    // 删除活动
    public void DeleteEvent(DeleteEventReq req);
    // 获取指定id活动信息
    public Events getEventById(Integer id);
    // 批量获取指定id活动标题
    public Map<Integer, String> getTitlesByIds(List<Integer> eventIds);
    // 批量获取指定id活动信息
    public Map<Integer, Events> getInfosByIds(List<Integer> eventIds);

}
