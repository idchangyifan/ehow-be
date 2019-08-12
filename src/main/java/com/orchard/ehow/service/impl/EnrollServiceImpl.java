package com.orchard.ehow.service.impl;

import com.orchard.ehow.dao.EnrollEnrollsinfo;
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
}
