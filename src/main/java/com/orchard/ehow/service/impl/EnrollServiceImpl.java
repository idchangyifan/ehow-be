package com.orchard.ehow.service.impl;

import com.orchard.ehow.dao.EnrollEnrollsinfo;
import com.orchard.ehow.dao.EnrollEnrollsinfoExample;
import com.orchard.ehow.dao.EnrollProject;
import com.orchard.ehow.dao.EnrollProjectExample;
import com.orchard.ehow.mapper.EnrollEnrollsinfoMapper;
import com.orchard.ehow.mapper.EnrollProjectMapper;
import com.orchard.ehow.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Orchard Chang
 * @date 2019/8/4 0004 11:20
 * @Description
 */
@Service
public class EnrollServiceImpl implements EnrollService {
    @Autowired
    EnrollEnrollsinfoMapper enrollEnrollsinfoMapper;
    @Autowired
    EnrollProjectMapper enrollProjectMapper;
    @Override
    public void enroll(EnrollEnrollsinfo enrollEnrollsinfo) {
        enrollEnrollsinfoMapper.insert(enrollEnrollsinfo);
    }

    @Override
    public List<EnrollProject> getAllProjects() {
        EnrollProjectExample example = new EnrollProjectExample();
        return enrollProjectMapper.selectByExample(example);
    }


    @Override
    public List<EnrollEnrollsinfo> getEnrollEnrollsinfoByProjectCode(String projectCode) {
        EnrollEnrollsinfoExample example = new EnrollEnrollsinfoExample();
        return enrollEnrollsinfoMapper.selectByExample(example.createCriteria().andProjectCodeEqualTo(projectCode).example());
//        return enrollEnrollsinfoMapper.selectByExample(example);
    }

    @Override
    public EnrollEnrollsinfo findEnrollsInfoByUserIdAndProjectCode(String userId, String projectCode) {
        EnrollEnrollsinfoExample example = new EnrollEnrollsinfoExample();
        return enrollEnrollsinfoMapper.selectByExample(example.createCriteria().andProjectCodeEqualTo(projectCode)
                .andUseridEqualTo(userId).example()).stream().findFirst().orElse(null);
    }
}
