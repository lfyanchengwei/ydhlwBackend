package com.ydhlw.serve.controller;


import com.ydhlw.common.result.Result;
import com.ydhlw.pojo.dto.req.UserLoginReq;
import com.ydhlw.pojo.dto.req.UserRegReq;
import com.ydhlw.pojo.dto.resp.UserFindByIdResp;
import com.ydhlw.pojo.dto.resp.UserLoginResp;
import com.ydhlw.serve.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    //用户注册
    @PostMapping("/register")
    public Result<String> userReg(@RequestBody UserRegReq req){
        try {
            // 调用 Service 层的逻辑
            usersService.userReg(req);
            return Result.success("注册成功"); // 返回成功消息
        } catch (Exception e) {
            return Result.error("注册失败: " + e.getMessage()); // 返回错误信息
        }
    }

    //用户登录
    @PostMapping("/login")
    public Result<UserLoginResp> userLogin(@RequestBody UserLoginReq req) {
        try {
            UserLoginResp user = usersService.userLogin(req);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("登录失败: " + e.getMessage());
        }
    }

    //根据用户id查询用户信息
    @GetMapping("/findById")
    public Result<UserFindByIdResp> userFindById(@RequestParam("userId") Integer userId) {
        try {
            UserFindByIdResp user = usersService.userFindById(userId);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

}
