package com.ydhlw.pojo.dao.mapper;

import com.ydhlw.pojo.dao.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    // 自定义批量查询方法
    List<Users> selectByIds(@Param("ids") List<Integer> ids);

}
