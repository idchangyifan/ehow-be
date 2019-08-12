package com.orchard.ehow.api;

import cn.hutool.core.collection.CollectionUtil;
import com.orchard.ehow.dao.EnrollEnrollsinfo;
import com.orchard.ehow.service.EnrollService;
import com.orchard.ehow.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

}
