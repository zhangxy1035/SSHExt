<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="admin/extjs4.2/bootstrap.js"></script>
	<script type="text/javascript" src="admin/extjs4.2/locale/ext-lang-zh_CN.js"></script>
	<link rel="stylesheet" type="text/css" href="admin/extjs4.2/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="admin/extjs4.2/resources/css/login.css">
	<script type="text/javascript" src="admin/main/index.js"></script>

  </head>
  
  <body>
    <div id="top" class="index_top w_100 center"></div>
    <iframe id="contentIframe" name="contentIframe" style="height:100%;width:100%" frameborder="0"></iframe>
  </body>
</html>
