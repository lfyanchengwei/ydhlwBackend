package com.ydhlw.pojo.dao.mapper;

import com.ydhlw.pojo.dao.entity.Events;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydhlw.pojo.dao.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Mapper
public interface EventsMapper extends BaseMapper<Events> {
    // 自定义批量查询方法
    List<Events> selectByIds(@Param("ids") List<Integer> ids);
}
