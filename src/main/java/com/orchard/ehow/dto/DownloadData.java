package com.orchard.ehow.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Orchard Chang
 * @date 2019/10/27 0027 0:03
 * @Description
 */
@Data
public class DownloadData {
    @ExcelProperty("公司名称")
    String companyName;

    @ExcelProperty("国家/地区")
    String regionName;

    @ExcelProperty("公司主要业绩1")
    String companyAchievement1;

    @ExcelProperty("公司主要业绩2")
    String companyAchievement2;

    @ExcelProperty("公司主要业绩3")
    String companyAchievement3;

    @ExcelProperty("公司主要业绩4")
    String companyAchievement4;

    @ExcelProperty("公司主要业绩5")
    String companyAchievement5;

    @ExcelProperty("主创团队")
    String teamMember;

    @ExcelProperty("主创业绩1")
    String memberAchievement1;

    @ExcelProperty("主创业绩2")
    String memberAchievement2;

    @ExcelProperty("主创业绩3")
    String memberAchievement3;

    @ExcelProperty("主创业绩4")
    String memberAchievement4;

    @ExcelProperty("主创业绩5")
    String memberAchievement5;

    @ExcelProperty("主创获奖")
    String memberAward;

    @ExcelProperty("联系人")
    String contact;

    @ExcelProperty("联系人手机")
    String phoneNumber;

    @ExcelProperty("联系人邮箱")
    String emailAddress;
}
