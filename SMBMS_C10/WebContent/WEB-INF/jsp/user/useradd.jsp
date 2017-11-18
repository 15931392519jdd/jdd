<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'useradd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <f:form method="post" modelAttribute="user">
        <p><f:errors path="userCode"></f:errors></p>
        <p>用户编码:<f:input path="userCode"/></p>
        <p><f:errors path="userName"></f:errors></p>
        <p>用户名称:<f:input path="userName"/></p>
        <p><f:errors path="userPassword"></f:errors></p>
        <p>用户密码:<f:input path="userPassword"/></p>
        <p>用户性别:<f:select path="gender">
        <f:option value="1" selected="selected">男</f:option>
        <f:option value="2">女</f:option>
        </f:select>
        </p>
        <p>用户生日:<f:input path="birthday" readonly="readonly"/></p>
        <p>用户电话:<f:input path="phone" /></p>
        <p>用户地址:<f:input path="address" /></p>
        <p>用户角色:<f:radiobutton path="userRole" value="1"/>系统管理员
        <f:radiobutton path="userRole" value="2"/>经理
        <f:radiobutton path="userRole" value="3"/>普通用户
        </p>
        <input type="submit" value="提交">
    </f:form>
    
  </body>
  <script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
</html>
