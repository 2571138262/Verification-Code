package com.imooc.code;

import com.google.code.kaptcha.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 获取浏览器的输出流
        PrintWriter out = response.getWriter();

        // 获取用户传递过来的验证码
        String code = request.getParameter("code");
        // 获取验证码框架产生的验证码
        String sessionCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("sessionCode ===========" + sessionCode);
        if (code != null && sessionCode != null){
            // 如果用户输入的验证码和产生在服务器端生成的验证码一致， 那么就告诉用户输入正确
            if (code.equalsIgnoreCase(sessionCode)){
                // 登录逻辑，注册逻辑等相关的业务操作
                out.print("success");
            }else{
                out.print("fail");
            }
        }else{
            out.print("fail");
        }

        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
