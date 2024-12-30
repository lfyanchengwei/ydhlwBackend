package com.ydhlw.serve.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydhlw.common.exception.LoginErrorException;
import com.ydhlw.common.utils.BCryptUtil;
import com.ydhlw.pojo.dao.entity.Users;
import com.ydhlw.pojo.dao.mapper.UsersMapper;
import com.ydhlw.pojo.dto.req.UserLoginReq;
import com.ydhlw.pojo.dto.req.UserRegReq;
import com.ydhlw.pojo.dto.resp.UserFindByIdResp;
import com.ydhlw.pojo.dto.resp.UserLoginResp;
import com.ydhlw.serve.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private UsersMapper userMapper;
    //用户注册
    @Override
    @Transactional
    public void userReg(UserRegReq req) {
        //检查用户名是否已存在
        if (count(new QueryWrapper<Users>().eq("email", req.getEmail())) > 0) {
            throw new IllegalArgumentException("Email already exists");
        }
        Users user = new Users();
        user.setUsername(req.getUsername());
        user.setPassword(BCryptUtil.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setCreatedAt(LocalDateTime.now());
        save(user);
    }
    //用户登录
    @Override
    public UserLoginResp userLogin(UserLoginReq req) {
        // 查询用户
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", req.getEmail());
        Users user = this.getOne(queryWrapper);
        if (user == null || !BCryptUtil.matches(req.getPassword(), user.getPassword())) {
            throw new LoginErrorException("用户名或密码错误");
        }
        return UserLoginResp.builder()
                .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
            .role(user.getRole())
            .createdAt(user.getCreatedAt())
            .build();
    }
    @Override
    public UserFindByIdResp userFindById(Integer userId) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Users user = this.getOne(queryWrapper);
        return UserFindByIdResp.builder()
            .userId(user.getUserId())
            .userName(user.getUsername())
            .email(user.getEmail())
            .phone(user.getPhone())
                .build();
    }

    public Map<Integer, String> getUsernamesByIds(List<Integer> userIds){
        List<Users> users = userMapper.selectByIds(userIds);
        return users.stream()
                .collect(Collectors.toMap(Users::getUserId, Users::getUsername, (a, b) -> a)); // 防止重复 key 报错
    }

    public Map<Integer, Users> getUserinfoByIds(List<Integer> userIds){
        List<Users> users = userMapper.selectByIds(userIds);
        return users.stream()
                .collect(Collectors.toMap(Users::getUserId, user -> user)); // 防止重复 key 报错
    }
}
