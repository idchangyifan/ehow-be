package com.orchard.ehow.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.google.common.collect.Lists;
import com.orchard.ehow.dao.*;
import com.orchard.ehow.dto.DownloadData;
import com.orchard.ehow.dto.Result;
import com.orchard.ehow.dto.UnionType;
import com.orchard.ehow.mapper.EnrollEnrollsinfoMapper;
import com.orchard.ehow.mapper.EnrollProjectMapper;
import com.orchard.ehow.service.EnrollService;
import com.orchard.ehow.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Orchard Chang
 * @date 2019/8/4 0004 11:23
 * @Description
 */
@RestController
@RequestMapping("/api")
@Validated

public class EnrollApi {
    @Autowired
    EnrollService enrollService;
    @Autowired
    EnrollEnrollsinfoMapper mapper;
    @Autowired
    EnrollProjectMapper enrollProjectMapper;

    @GetMapping("/findEnrollsInfoByUserIdAndProjectCode/{projectCode}")
    public Result<EnrollEnrollsinfo> findEnrollsInfoByUserIdAndProjectCode(@PathVariable("projectCode") String projectCode) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        String userId = userInfo.getUserId();
        return Result.success(enrollService.findEnrollsInfoByUserIdAndProjectCode(userId, projectCode));
    }

    /**
     * saveOrUpdate
     *
     * @param enrollEnrollsinfo
     * @return
     */
    @PostMapping("/enroll")
    public Object enroll(@RequestBody EnrollEnrollsinfo enrollEnrollsinfo) {
        LocalDateTime enrollDate = LocalDateTime.now();
        LocalDateTime expireDate = enrollEnrollsinfo.getExpireDate();
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        String userId = userInfo.getUserId();
        if (enrollDate.isAfter(expireDate)) {
            throw new RuntimeException("报名时间已截止！！");
        }
        enrollEnrollsinfo.setEnrollDate(enrollDate);
        enrollEnrollsinfo.setCompanyName(userInfo.getCompanyName());
        enrollEnrollsinfo.setRegionName(userInfo.getCompanyRegion());
        enrollEnrollsinfo.setContact(userInfo.getContact());
        enrollEnrollsinfo.setPhoneNumber(userInfo.getTelNumber());
        enrollEnrollsinfo.setEmailAddress(userInfo.getEmail());
        if (StringUtils.equals(UnionType.INDEPENDENCE.getUnionTypeCode(), enrollEnrollsinfo.getUnionTypeCode())) {
            enrollEnrollsinfo.setUnionTypeName(UnionType.INDEPENDENCE.getUnionTypename());
        } else if (StringUtils.equals(UnionType.AS_LEADER.getUnionTypeCode(), enrollEnrollsinfo.getUnionTypeCode())) {
            enrollEnrollsinfo.setUnionTypeName(UnionType.AS_LEADER.getUnionTypename());
        } else {
            enrollEnrollsinfo.setUnionTypeName(UnionType.AS_FOLLOWER.getUnionTypename());
        }
        //因为userId只能由后台设置，所以前端若传过来了，说明是修改
        if (StringUtils.isNotBlank(enrollEnrollsinfo.getUserid())) {
            EnrollEnrollsinfoExample example = new EnrollEnrollsinfoExample();
            example = example.createCriteria().andUseridEqualTo(userId).andProjectCodeEqualTo(enrollEnrollsinfo.getProjectCode()).example();
            mapper.updateByExample(enrollEnrollsinfo, example);
        } else {
            enrollEnrollsinfo.setUserid(userId);
            enrollService.enroll(enrollEnrollsinfo);
        }
        return ResponseUtil.ok();
    }

    @GetMapping("/getProjectList")
    public Object getAllProjects() {
        return ResponseUtil.ok(CollectionUtil.reverse(CollectionUtil.sortByProperty(enrollService.getAllProjects(), "expireDate")));
    }


    @GetMapping("/download/{projectCode}")
    public void downloadWithAuth(HttpServletResponse response, HttpServletRequest request, @PathVariable String projectCode) throws Exception {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String pass;
        if (user == null) {
            try {
                response.setCharacterEncoding("GBK");
//                PrintWriter out = response.getWriter();
                String authorization = request.getHeader("authorization");
                if (authorization == null || authorization.equals("")) {
                    response.setStatus(401);
                    response.setHeader("WWW-authenticate", "Basic realm=\"请输入管理员密码\"");
                    return;
                }
                String userAndPass = new String(new BASE64Decoder().decodeBuffer(authorization.split(" ")[1]));
                if (userAndPass.split(":").length < 2) {
                    response.setStatus(401);
                    response.setHeader("WWW-authenticate", "Basic realm=\"请输入管理员密码\"");
                    return;
                }
                user = userAndPass.split(":")[0];
                pass = userAndPass.split(":")[1];
                if (user.equals("ehow") && pass.equals("ehowsz1234")) {
                    session.setAttribute("user", user);
                    this.download(response,projectCode);
                } else {
                    response.setStatus(401);
                    response.setHeader("WWW-authenticate", "Basic realm=\"请输入管理员密码\"");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            this.download(response,projectCode);
        }
    }

    private void download(HttpServletResponse response, String projectCode) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("报名信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<EnrollEnrollsinfo> enrollEnrollsinfoList = enrollService.getEnrollEnrollsinfoByProjectCode(projectCode);
        List<DownloadData> downloadDataList = Lists.newArrayList();
        for (EnrollEnrollsinfo enrollEnrollsinfo : enrollEnrollsinfoList) {
            DownloadData downloadData = new DownloadData();
            BeanUtil.copyProperties(enrollEnrollsinfo, downloadData);
            downloadData.setMemberAchievement2(enrollEnrollsinfo.getMemberAchievement1() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement2() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement3() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement4() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement5() + "/n");
            downloadData.setCompanyAchievement2(enrollEnrollsinfo.getCompanyAchievement1() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement2() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement3() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement4() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement5() + "/n");

            downloadDataList.add(downloadData);
        }
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("报名信息").doWrite(downloadDataList);
    }

    @GetMapping("getProjectByCode/{projectCode}")
    public Result<EnrollProject> getProjectByCode(@PathVariable String projectCode) {
        EnrollProjectExample example = new EnrollProjectExample();
        example.createCriteria().andProjectCodeEqualTo(projectCode);
        return Result.success(enrollProjectMapper.selectByExample(example).get(0));
    }

    @ApiOperation("检查所填写的牵头公司是否已经报名")
    @GetMapping("/checkLeaderCodeExist/{leaderCode}")
    public Result<?>  getLeaderCode(@PathVariable String leaderCode) {
        EnrollEnrollsinfoExample example = new EnrollEnrollsinfoExample();
        example.createCriteria().andLeaderCompanyIdEqualTo(leaderCode).andUnionTypeCodeEqualTo("1");
        if (mapper.selectByExample(example).size() != 0) {
            return Result.success();
        } else {
            throw new RuntimeException("牵头公司代码有误，请确保牵头公司已报名成功！");
        }
    }

}
