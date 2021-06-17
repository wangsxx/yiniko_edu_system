package com.wangs.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.commonutils.JwtUtils;
import com.wangs.commonutils.MD5;
import com.wangs.commonutils.R;
import com.wangs.user.entity.UcenterMember;
import com.wangs.user.entity.vo.RegisterVo;
import com.wangs.user.mapper.UcenterMemberMapper;
import com.wangs.user.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-25
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String login(UcenterMember user) {
        // 获得手机号和密码
        String mobile = user.getMobile();
        String pwd = user.getPassword();

        // 手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pwd)){
            return null;
        }

        // 判断手机号是否存在
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) return null;

        // 判断密码以及是否禁用，密码使用MD5加密
        if(!ucenterMember.getIsDisabled() && ucenterMember.getPassword().equals(MD5.encrypt(pwd))){
            String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return token;
        }else{
            return null;
        }
    }

    public R register(RegisterVo registerVo) {
        // 获取注册信息
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String pwd = registerVo.getPassword();

        // 非空判断
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(pwd)){
            return R.error().message("数据存在空值！");
        }
        // 判断验证码是否正确，使用Redis，都存在Redis中
        String redis_code = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redis_code)){
            return R.error().message("验证码不正确！");
        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            return R.error().message("手机号重复注册！");
        }

        // 数据添加到数据库
        UcenterMember user = new UcenterMember();
        user.setMobile(mobile);
        user.setPassword(MD5.encrypt(pwd));
        user.setNickname(nickname);
        baseMapper.insert(user);
        return R.ok();
    }

    // 根据openid查询数据
    public UcenterMember getByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>();
        wrapper.eq("openid", openid);
        UcenterMember user = baseMapper.selectOne(wrapper);
        return user;
    }
}
