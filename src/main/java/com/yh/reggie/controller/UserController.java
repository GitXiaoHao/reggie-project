package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yh.reggie.common.Information;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.User;
import com.yh.reggie.service.UserService;
import com.yh.reggie.utils.SMSUtils;
import com.yh.reggie.utils.ValidateCodeUtils;


import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 咕咕猫
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    /**
     * 发送手机短信验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("阿里云短信测试","SMS_266130121",phone,code);

            //缓存到redis中
            try {
                redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            } catch (Exception e) {
                session.setAttribute(phone,code);
                log.error("redis可能未开启");
            }
            return Result.success("手机验证码短信发送成功");
        }
        return Result.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     *
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从redis中获取保存的验证码
        Object codeInSession ;
        try {
            codeInSession =  redisTemplate.opsForValue().get(phone);
        } catch (Exception e) {
            codeInSession = session.getAttribute(phone);
            log.error("redis可能未开启");
        }
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (codeInSession != null && codeInSession.equals(code)) {
            //如果能够比对成功，说明登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            //根据用户的手机号去用户表获取用户
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                //可设置也可不设置，因为数据库我们设置了默认值
                user.setStatus(1);
                //注册新用户
                userService.save(user);
            }
            //在session中保存用户的登录状态,这样过滤器就不会被拦截了
            session.setAttribute(Information.USER, user.getId());
            try {
                //删除验证码
                redisTemplate.delete(phone);
            } catch (Exception e) {
                log.error("redis可能未开启");
            }
            return Result.success(user);
        }
        return Result.error("登录失败");
    }
}