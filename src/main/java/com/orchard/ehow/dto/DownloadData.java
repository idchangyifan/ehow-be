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

    @ExcelProperty("公司主要业绩")
    String companyAchievement;

    @ExcelProperty("公司主要业绩(旧版)")
    String companyAchievement2;


    @ExcelProperty("主创团队")
    String teamMember;

    @ExcelProperty("主创业绩")
    String memberAchievement;

    @ExcelProperty("主创业绩（旧版）")
    String memberAchievement2;

    @ExcelProperty("主创获奖")
    String memberAward;

    @ExcelProperty("联系人")
    String contact;

    @ExcelProperty("联系人手机")
    String phoneNumber;

    @ExcelProperty("联系人邮箱")
    String emailAddress;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCompanyAchievement() {
        return companyAchievement;
    }

    public void setCompanyAchievement(String companyAchievement) {
        this.companyAchievement = companyAchievement;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public String getMemberAchievement() {
        return memberAchievement;
    }

    public void setMemberAchievement(String memberAchievement) {
        this.memberAchievement = memberAchievement;
    }

    public String getMemberAward() {
        return memberAward;
    }

    public void setMemberAward(String memberAward) {
        this.memberAward = memberAward;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCompanyAchievement2() {
        return companyAchievement2;
    }

    public void setCompanyAchievement2(String companyAchievement2) {
        this.companyAchievement2 = companyAchievement2;
    }

    public String getMemberAchievement2() {
        return memberAchievement2;
    }

    public void setMemberAchievement2(String memberAchievement2) {
        this.memberAchievement2 = memberAchievement2;
    }
}
