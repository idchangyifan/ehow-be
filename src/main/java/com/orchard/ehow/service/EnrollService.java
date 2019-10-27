package com.orchard.ehow.service;

import com.orchard.ehow.dao.EnrollEnrollsinfo;
import com.orchard.ehow.dao.EnrollProject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Orchard Chang
 * @date 2019/8/4 0004 10:56
 * @Description
 */
@Service
public interface EnrollService {
    /**
     * 报名
     *
     * @param enrollEnrollsinfo
     */
    void enroll(EnrollEnrollsinfo enrollEnrollsinfo);

    /**
     * 查找所有的项目
     * @return
     */
    List<EnrollProject> getAllProjects();


    List<EnrollEnrollsinfo> getEnrollEnrollsinfoByProjectCode(String projectCode);

}
