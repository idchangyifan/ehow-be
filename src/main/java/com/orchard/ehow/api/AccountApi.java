package com.orchard.ehow.api;

import com.orchard.ehow.dao.UserInfo;
import com.orchard.ehow.dao.UserInfoExample;
import com.orchard.ehow.dto.Result;
import com.orchard.ehow.dto.UserInfoDto;
import com.orchard.ehow.mapper.UserInfoMapper;
import com.orchard.ehow.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Author orchard.chang
 * @Date 2018/10/2616:37
 * @Version 1.0
 **/
@Api(tags = {"通用接口"})
@RestController
@Validated
public class AccountApi {
    @Autowired
    AccountService accountService;
    @Autowired
    UserInfoMapper mapper;

    //TODO:数据校验这一块重写 合理利用@valid
    @ApiOperation("注册")
    @PostMapping("/anno/register")
    public Result<?> register(@RequestBody UserInfoDto userInfoDto) {
        return Result.success(accountService.register(userInfoDto));
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserInfoDto userInfoDto) {
        try {
            accountService.login(userInfoDto);
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(userInfo, userInfoDto);
            userInfoDto.setPassword(null);
            return Result.success(userInfoDto);
        } catch (RuntimeException e) {
            return Result.failureMsg("账号或密码错误！");
        }
    }

    @ApiOperation("查看个人信息")
    @GetMapping("/api/getUserInfo")
    public Result<UserInfoDto> getUserInfo() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return Result.success(null);
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo, userInfoDto);
        userInfoDto.setPassword("");
        userInfoDto.setPassword2("");
        userInfoDto.setCompanyAddress(userInfo.getCompanyRegion());
        return Result.success(userInfoDto);
    }

    @ApiOperation("校验用户名是否可用")
    @GetMapping("/anno/checkIdAvailable/{userId}")
    public Result<Boolean> checkIdAvailable(@PathVariable("userId") String userId) {
        return Result.success(accountService.findByUserId(userId) == null);
    }

    @ApiOperation("修改个人信息")
    @PostMapping("/api/modifyUserInfo")
    public Result<?> modifyUserInfo(@RequestBody UserInfoDto userInfoDto) {
        UserInfo userInfoBefore = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (!StringUtils.equals(userInfoDto.getUserId(), userInfoBefore.getUserId())) {
            throw new RuntimeException("系统出现内部错误，请关闭整个网页并使用无痕窗口重进登录");
        }
        userInfoBefore.setCompanyName(userInfoDto.getCompanyName());
        userInfoBefore.setCompanyRegion(userInfoDto.getCompanyAddress());
        userInfoBefore.setIncorporator(userInfoDto.getIncorporator());
        userInfoBefore.setContact(userInfoDto.getContact());
        userInfoBefore.setTelNumber(userInfoDto.getTelNumber());
        userInfoBefore.setEmail(userInfoDto.getEmail());
        userInfoBefore.setWebSite(userInfoDto.getWebSite());
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userInfoBefore.getUserId());
        mapper.updateByExample(userInfoBefore, example);
        return Result.success();
    }

    @ApiOperation("获取专属牵头公司代码")
    @GetMapping("/api/getLeaderCode")
    public Result<?>  getLeaderCode() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return Result.success(userInfo.getLeaderCode());
    }


}
