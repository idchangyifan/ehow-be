package com.orchard.ehow.dto;

/**
 * @Author orchard.chang
 * @Date 2018/10/1810:10
 * @Version 1.0
 **/
public class UserInfoDto {

    private String userId;

    private String password;

    private String password2;

    private String telNumber;

    private String contact;

    private String companyName;

    private String email;

    private String incorporator;

    private String webSite;

    private String companyAddress;


    public String getIncorporator() {
        return incorporator;
    }

    public void setIncorporator(String incorporator) {
        this.incorporator = incorporator;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
