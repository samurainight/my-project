package com.my.web.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.web.api.util.ApiReponse;
import com.my.web.common.IRedisService;
import com.my.web.entity.Role;
import com.my.web.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    @Reference
    private TestService testService;
    @Reference
    private IRedisService redisService;
    @GetMapping("/test")
    public ApiReponse test(HttpServletRequest request){
        ApiReponse apiReponse=new ApiReponse();
        List<Role> list=testService.getName("role");
        request.getSession().setAttribute("name","wangzhipeng");
        String name=(String)request.getSession().getAttribute("name");
        if(CollectionUtils.isEmpty(list)){
            apiReponse.setData("找不到缓存");
        }else{
            apiReponse.setData(list);
            redisService.clearValue("role");
            redisService.clearValue("name");
        }
        return apiReponse;
    }
    @GetMapping("/test/dao")
    public ApiReponse fetchRoleList(){
        ApiReponse apiReponse=new ApiReponse();
        List<Role> list=testService.fetchRoleList();
        redisService.setList("role",list);
        redisService.set("name","wangzhipeng");
        apiReponse.setData(list);
        return apiReponse;
    }
}
