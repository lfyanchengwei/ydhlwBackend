package com.ydhlw.serve.controller;


import com.ydhlw.common.result.Result;
import com.ydhlw.pojo.dto.req.RegisterEventReq;
import com.ydhlw.serve.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 报名表 前端控制器
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    private IRegistrationService registrationService;

    //用户报名
    @PostMapping("/registerEvent")
    public Result<String> registerEvent(@RequestBody RegisterEventReq req){
        try{
            registrationService.registerEvent(req);
            return Result.success("报名成功，请等待审核");
        }catch(Exception e){
            return Result.error("报名失败"+e.getMessage());
        }
    }

    //报名列表
    @GetMapping("/list")
    public Result<Object> listRegistrations(@RequestParam(value = "eventId", required = false) Integer eventId, @RequestParam(value = "pageNum",defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        // 调用 Service 层方法获取报名记录
        return Result.success(registrationService.listRegistrations(eventId, pageNum, pageSize));
    }

    //分页获取指定活动的待审核的报名列表
    @GetMapping("/listPending")
    public Result<Object> listPendingRegistrations(@RequestParam(value = "eventId", required = false) Integer eventId, @RequestParam(value = "pageNum",defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        // 调用 Service 层方法获取报名记录
        return Result.success(registrationService.listPendingRegistrations(eventId,pageNum,pageSize));
    }
    //获取用户某活动报名状态
    @GetMapping("/getUserRegistrationStatus")
    public Result<String> getUserRegistrationStatus(@RequestParam("eventId") Integer eventId,
                                                   @RequestParam("userId") Integer userId) {
        try {
            String status = registrationService.getUserRegistrationStatus(eventId, userId);
            return Result.success(status);
        } catch (Exception e) {
            return Result.error("获取报名状态失败：" + e.getMessage());
        }
    }

    //更新报名状态
    @GetMapping("/updateRegistrationStatus")
    public Result<String> updateRegistrationStatus(@RequestParam("registrationId") Integer registrationId,
                                                  @RequestParam("status") String status) {
        try {
            registrationService.updateRegistrationStatus(registrationId, status);
            return Result.success("更新报名状态成功");
        } catch (Exception e) {
            return Result.error("更新报名状态失败：" + e.getMessage());
        }
    }

    //分页获取指定活动的报名已通过列表
    @GetMapping("/listPassed")
    public Result<Object> listPassedRegistrations(@RequestParam("eventId") Integer eventId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 调用 Service 层方法获取报名记录
        return Result.success(registrationService.listPassedRegistrations(eventId,pageNum,pageSize));
    }

    //用户获取活动参加列表
    @GetMapping("/listUsersParticipation")
    public Result<Object> listUsersParticipation(@RequestParam("userId") Integer userId) {
        // 调用 Service 层方法获取报名记录
        return Result.success(registrationService.listUsersParticipation(userId));
    }

    //用户获取活动报名列表
    @GetMapping("/listUsersRegistrations")
    public Result<Object> listUsersRegistrations(@RequestParam("userId") Integer userId) {
        // 调用 Service 层方法获取报名记录
        return Result.success(registrationService.listUsersRegistrations(userId));
    }

}
