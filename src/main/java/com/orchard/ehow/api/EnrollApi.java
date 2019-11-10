package com.orchard.ehow.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.google.common.collect.Lists;
import com.orchard.ehow.dao.EnrollEnrollsinfo;
import com.orchard.ehow.dto.DownloadData;
import com.orchard.ehow.mapper.EnrollEnrollsinfoMapper;
import com.orchard.ehow.service.EnrollService;
import com.orchard.ehow.util.ResponseUtil;
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


    @PostMapping("/enroll")
    Object enroll(@RequestBody EnrollEnrollsinfo enrollEnrollsinfo) {
        LocalDateTime enrollDate = LocalDateTime.now();
        LocalDateTime expireDate = enrollEnrollsinfo.getExpireDate();
        if (enrollDate.isAfter(expireDate)) {
            throw new RuntimeException("报名时间已截止！！");
        }
        enrollEnrollsinfo.setEnrollDate(enrollDate);
        enrollService.enroll(enrollEnrollsinfo);
        return ResponseUtil.ok();
    }

    @GetMapping("/getProjectList")
    Object getAllProjects() {
        return ResponseUtil.ok(CollectionUtil.reverse(CollectionUtil.sortByProperty(enrollService.getAllProjects(), "expireDate")));
    }


    @GetMapping("/download")
    public void downloadWithAuth(HttpServletResponse response, HttpServletRequest request) throws Exception {
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
                    this.download(response);
                } else {
                    response.setStatus(401);
                    response.setHeader("WWW-authenticate", "Basic realm=\"请输入管理员密码\"");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            this.download(response);
        }
    }

    private void download(HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("报名信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<EnrollEnrollsinfo> enrollEnrollsinfoList = enrollService.getEnrollEnrollsinfoByProjectCode("00003");
        List<DownloadData> downloadDataList = Lists.newArrayList();
        for (EnrollEnrollsinfo enrollEnrollsinfo : enrollEnrollsinfoList) {
            DownloadData downloadData = new DownloadData();
            BeanUtil.copyProperties(enrollEnrollsinfo, downloadData);
            downloadData.setMemberAchievement(enrollEnrollsinfo.getMemberAchievement1() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement2() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement3() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement4() + "/n" +
                    enrollEnrollsinfo.getMemberAchievement5() + "/n");
            downloadData.setCompanyAchievement(enrollEnrollsinfo.getCompanyAchievement1() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement2() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement3() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement4() + "/n" +
                    enrollEnrollsinfo.getCompanyAchievement5() + "/n");
            downloadDataList.add(downloadData);
        }
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("报名信息").doWrite(downloadDataList);
    }

}
