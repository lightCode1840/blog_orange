package com.light.service.impl;

import com.light.domin.ResponseResult;
import com.light.domin.entity.LoginUser;
import com.light.domin.entity.User;
import com.light.service.BlogLoginService;
import com.light.utils.BeanCopyUtils;
import com.light.utils.JwtUtil;
import com.light.utils.RedisCache;
import com.light.vo.BlogUserLoginVo;
import com.light.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lilepingstart
 * @creat 2023-07-01 18:41
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        authenticationManager.authenticate(authenticationToken);
        //获取用户ID，生成JWT
        //判断是否认证通过
        if(Objects.isNull(authenticationToken)){
            throw new RuntimeException("用户名或密码错误");
        }
        //把用户信息存入redis
        LoginUser loginUser= (LoginUser) authenticationToken.getPrincipal();
        String userId=loginUser.getUser().getId().toString();
        String jwt=JwtUtil.createJWT(userId);
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装返回
        //把User转换成UserInfo
        UserInfoVo userInfoVo=BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo=new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
