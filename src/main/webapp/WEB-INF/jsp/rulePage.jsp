<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>WAF规则配置</title>
    </head>
    <body>
        <h2>WAF配置规则如下：</h2>
        <table border="1.0">
            <tr>
                <th><label>编号：</label></th>
                <th><label>内容：</label></th>
            </tr>
            <c:forEach items="${rules}" var="rule">
                <tr><td>${rule.id}</td><td>${rule.content}</td></tr>
            </c:forEach>
        </table>
        <input type="button" id="add" value="添加" onclick=location.href="http://127.0.0.1:8080/rules/add" />
        <input type="button" id="del" value="删除" onclick=location.href="http://127.0.0.1:8080/rules/del" />
        <input type="button" id="modify" value="修改" onclick=location.href="http://127.0.0.1:8080/rules/modify" />
    </body>
</html>
