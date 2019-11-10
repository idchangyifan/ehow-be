package com.orchard.ehow.service;

import com.orchard.ehow.dao.UserInfo;
import com.orchard.ehow.dto.UserInfoDto;


/**
 *@Author orchard.chang
 *@Date 2018/10/1715:04
 *@Version 1.0
 **/
public interface AccountService {
    /**
     * 注册
     *
     * @param userInfoDto
     * @return
     */
    boolean register(UserInfoDto userInfoDto);

    /**
     * 登陆
     *
     * @param userInfoDto
     * @return
     */
    boolean login(UserInfoDto userInfoDto);



    /**
     * 根据用户账号查找用户信息
     * @param userId
     * @return
     */
    UserInfo findByUserId(String userId);


}
