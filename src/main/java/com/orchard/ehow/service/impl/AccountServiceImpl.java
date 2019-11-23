package com.orchard.ehow.service.impl;
/**
 * @author Orchard.Chang
 */

import cn.hutool.core.util.StrUtil;
import com.orchard.ehow.dao.UserInfo;
import com.orchard.ehow.dao.UserInfoExample;
import com.orchard.ehow.dto.UserInfoDto;
import com.orchard.ehow.mapper.UserInfoMapper;
import com.orchard.ehow.service.AccountService;
import org.apache.catalina.User;
import org.apache.commons.lang3.RandomStringUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import static com.orchard.ehow.dao.UserInfo.Column.userId;

/**
 * @Author orchard.chang
 * @Date 2018/10/2616:36
 * @Version 1.0
 **/
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    UserInfoMapper userInfoMapper;
    Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());


    @Override
    public boolean register(UserInfoDto userInfoDto) {

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfo);
        userInfo.setUserType(1);
        userInfo.setRegisterDate(LocalDate.now());
        userInfo.setCompanyRegion(userInfoDto.getCompanyAddress());
        String salt = RandomStringUtils.randomAlphanumeric(10) + userInfo.getUserId();
        userInfo.setSalt(salt);
        userInfo.setPassword(new SimpleHash("MD5", userInfo.getPassword(), salt, 5).toString());
        userInfo.setLeaderCode(UUID.randomUUID().toString());
        userInfoMapper.insert(userInfo);
        return true;

    }

    @Override
    public boolean login(UserInfoDto userInfoDto) {
        String userId = userInfoDto.getUserId();
        String password = userInfoDto.getPassword();
//        boolean rememberMe = userInfoDto.isRememberMe();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userId, password);
            try {
                //最终，会调用MyShiroRealm中override的doGetAuthenticationInfo方法
                currentUser.login(token);
                return true;
            } catch (AuthenticationException e) {
                throw new RuntimeException("账户或密码不正确");
            }
        }
        //若用户已经登录会返回false
//        UserInfo userInfo = (UserInfo)SecurityUtils.getSubject().getPrincipal();
//        logger.info(StrUtil.format("{}已经登录了！", userInfo.getContact()));
        return false;
    }



    @Override
    public UserInfo findByUserId(String userId) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userInfoMapper.selectByExample(example).stream().findFirst().orElse(null);
    }
}
