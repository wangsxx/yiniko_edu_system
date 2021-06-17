package com.wangs.user.service;

import com.wangs.commonutils.R;
import com.wangs.user.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangs.user.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-03-25
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember user);

    R register(RegisterVo registerVo);

    UcenterMember getByOpenId(String openid);
}
