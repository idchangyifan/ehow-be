package com.orchard.ehow.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.orchard.ehow.dto.Result;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.shiro.web.util.WebUtils.saveRequest;

/**
 * 前端传不了cookie是因为shiro的权限控制，由于复杂请求要传两次，第一次验证请求(OPTIONS)是没有带cookie的所以验证不通过，导致接下来的真实请求无法继续，因为上面的请求被拒绝了
 *
 * @author Orchard Chang
 * @date 2019/11/17 0017 17:20
 * @Description
 * 重写shiro的UserFilter，实现通过OPTIONS请求。UserFilter会过滤掉所有没有登录cookie的请求
 */
public class ShiroUserFilter extends UserFilter {
    /**
     * 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest,httpResponse);
            return true;
        }
        return super.preHandle(request,response);
    }

/*    *//**
     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
     * 因此重写改成传输JSON数据
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        setHeader((HttpServletRequest) request,(HttpServletResponse) response);
        PrintWriter out = response.getWriter();
        out.println(JSONUtil.toJsonStr(Result.failureMsg("沒有登陸")));
        out.flush();
        out.close();
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }
}
