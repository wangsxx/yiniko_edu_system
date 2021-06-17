package com.wangs.user.controller;


import com.wangs.commonutils.JwtUtils;
import com.wangs.commonutils.R;
import com.wangs.user.entity.UcenterMember;
import com.wangs.user.entity.vo.RegisterVo;
import com.wangs.user.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-25
 */
@RestController
@RequestMapping("/eduuser/user/")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public R login(@RequestBody UcenterMember user){
        // 使用service方法调用登录接口
        // 返回token，用于登录，使用JWT生成
        String token = ucenterMemberService.login(user);
        if (token == null) return R.error().message("登录失败！");
        return R.ok().data("token", token);
    }

    // 注册方法
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        return ucenterMemberService.register(registerVo);
    }

    // 根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        // 根据JWT工具类的方法，返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        // 根据id获取用户信息
        UcenterMember member = ucenterMemberService.getById(id);
        if(member.getPassword()!=null){
            member.setPassword("");
        }
        return R.ok().data("user", member);
    }
}

