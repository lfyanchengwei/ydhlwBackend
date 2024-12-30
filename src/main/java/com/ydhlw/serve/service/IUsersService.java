package com.ydhlw.serve.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ydhlw.pojo.dao.entity.Users;
import com.ydhlw.pojo.dto.req.UserLoginReq;
import com.ydhlw.pojo.dto.req.UserRegReq;
import com.ydhlw.pojo.dto.resp.UserFindByIdResp;
import com.ydhlw.pojo.dto.resp.UserLoginResp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public interface IUsersService extends IService<Users> {
    //用户注册
    public void userReg(UserRegReq req);
    //用户登录
    public UserLoginResp userLogin(UserLoginReq req);
    //根据用户id查找用户信息
    public UserFindByIdResp userFindById(Integer userId);
    //根据用户id集合批量查找用户名
    public Map<Integer, String> getUsernamesByIds(List<Integer> userIds);
    //根据用户id集合批量查找用户信息
    public Map<Integer, Users> getUserinfoByIds(List<Integer> userIds);
}
