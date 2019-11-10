package com.orchard.ehow.api;

import com.orchard.ehow.dto.Result;
import com.orchard.ehow.dto.UserInfoDto;
import com.orchard.ehow.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *@Author orchard.chang
 *@Date 2018/10/2616:37
 *@Version 1.0
 **/
@Api(tags = {"通用接口"})
@RestController
@Validated
public class AccountApi {
    @Autowired
    AccountService accountService;

    //TODO:数据校验这一块重写 合理利用@valid
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<?> register(@RequestBody UserInfoDto userInfoDto) {
        return Result.success(accountService.register(userInfoDto));
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserInfoDto userInfoDto) {
        if (accountService.login(userInfoDto)) {
            return Result.success(true);
        }
        return Result.success(false);
    }
}
