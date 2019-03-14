<%@ page import="com.imooc.code.CaptcahCode" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/27
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    // 1、清空浏览器的缓冲 因为浏览器会对网站的资源文件和图像进行记忆存储， 如果被浏览器加载过得图片就记忆起来，记忆后，
    //    文件就不会和服务器在交互，如果我们的验证码不清空的话，可能会造成一个问题：验证码刷新以后没有效果
    response.setHeader("pargma", "no-cache");
    response.setHeader("cache-control","no-cache");
    response.setHeader("expires", "0");

    // 2、调用编写的生成验证码的工具
    String code = CaptcahCode.drawImageVerificate(response);
    session.setAttribute("code", code);

    // 2、如何解决getOutputStream异常问题
    out.clear();
    out = pageContext.pushBody();

%>