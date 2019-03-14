<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/27
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Java验证码</title>
  </head>
  <body>
  <img src="code.jsp" alt="" id="code">
  <a href="javascript:void(0);" onclick="changeCode()">看不清?点我</a>
  <script>
    
    function changeCode() {
        document.getElementById("code").src = "code.jsp?d="+ new Date().getTime();
    }
    
  </script>
  </body>
</html>
