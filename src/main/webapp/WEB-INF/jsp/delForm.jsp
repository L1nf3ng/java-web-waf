<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>WAF规则删除</title>
        <script type="javascript">
            function back(){
                // request RULE page in GET method
            }
        </script>
    </head>
    <body>
        <h2>WAF配置规则删除：</h2>
        <form action="/rules/del" method="post">
            <h3>请填入规则序号：</h3>
            <input type="text" name="id" />
            <input type="button" name="cancel" value="取消" onclick=location.href="http://127.0.0.1:8080/rules/" />
            <input type="submit" value="确认" />
        </form>
    </body>
</html>