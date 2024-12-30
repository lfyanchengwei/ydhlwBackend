package com.ydhlw.serve.controller;


import com.ydhlw.common.result.Result;
import com.ydhlw.pojo.dto.req.DeleteEventReq;
import com.ydhlw.pojo.dto.req.EditEventReq;
import com.ydhlw.pojo.dto.req.NewEventReq;
import com.ydhlw.serve.service.IEventsService;
import com.ydhlw.serve.service.impl.EventsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author yanc
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/api/events")
public class EventsController {
    @Autowired
    private IEventsService eventsService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/new")
    public Result<String> newEvent(@RequestBody NewEventReq req) {
        try{
            eventsService.newEvent(req);
            return Result.success("创建成功");
        }catch(Exception e){
            return Result.error("创建失败: " + e.getMessage());
        }
    }

//    @GetMapping("/list")
//    public Result<Object> listEvent() {
//        try{
//            return Result.success(eventsService.listEvent());
//        }catch (Exception e){
//            return Result.error("获取失败: " + e.getMessage());
//        }
//    }
    // 分页查询活动
    @GetMapping("/list")
    public Result<Object> listEvent(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            return Result.success(eventsService.listEvent(pageNum, pageSize));
        } catch (Exception e) {
            return Result.error("获取失败: " + e.getMessage());
        }
    }
    //分页查询活动标题
    @GetMapping("/listIds")
    public Result<Object> listEventIds(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            return Result.success(eventsService.listEventIds());
        } catch (Exception e) {
            return Result.error("获取失败: " + e.getMessage());
        }
    }
    //更新活动
    @PostMapping("/edit")
    public Result<String> editEvent(@RequestBody EditEventReq req) {
        try{
            eventsService.editEvent(req);
            return Result.success("修改成功");
        }catch(Exception e){
            return Result.error("修改失败: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteEvent(@RequestBody DeleteEventReq req) {
        try{
            eventsService.DeleteEvent(req);
            return Result.success("删除成功");
        }catch(Exception e){
            return Result.error("删除失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取事件信息
     *
     * @param eventId 事件的唯一标识符
     * @return 返回一个Result对象，包含获取的事件信息或错误信息
     */
    @GetMapping("/get")
    public Result<Object> getEventById(@RequestParam("eventId") Integer eventId) {
        try{
            return Result.success(eventsService.getEventById(eventId));
        }catch (Exception e){
            return Result.error("获取失败: " + e.getMessage());
        }
    }

    //图片上传，返回图片Url
    @PostMapping("/upload")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空！");
        }

        try {
            String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads").toString();
            File directory = new File(uploadDir);
            // 创建上传目录
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件到服务器
            String originalFilename = file.getOriginalFilename();
            String filePath = Paths.get(uploadDir, originalFilename).toString();
            file.transferTo(new File(filePath));

            // 保存路径到数据库
            String imageUrl = "http://154.8.140.108:8883/uploads/" + originalFilename; // 你可以根据实际需求调整URL生成逻辑

            return Result.success(imageUrl);
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }

}
