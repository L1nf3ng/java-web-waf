<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>WAF规则修改</title>
        <script type="javascript">
            function back(){
                // request RULE page in GET method
            }
        </script>
    </head>
    <body>
        <h2>WAF配置规则修改：</h2>
        <form action="/rules/modify" method="post">
            <table border="1.0">
                <tr>
                    <td><label>请填入序号和规则：</label></td>
                </tr>
                <tr>
                    <td>序号：<input type="text" name="id" /></td>
                </tr>
                <tr>
                    <td>规则：<input type="text" name="content" style="width:1200px;"/></td>
                </tr>
                <!--  这里预设一个“+”按钮，点击会出现一个新的input栏   -->
                <!--  表单提交时需要将这些content一块提交，或者利用js整合提交  -->
            </table>
            <input type="button" name="cancel" value="取消" onclick=location.href="http://127.0.0.1:8080/rules/" />
            <input type="submit" value="确认" />
        </form>
    </body>
</html>