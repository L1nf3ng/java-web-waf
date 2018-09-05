# java-web-waf
A web GUI to configure waf based on nginx-lua module

简单的基于磁盘文件的增删改<br>
目前已经完成的接口有：<br>

① http://127.0.0.1:8080/rules/<br>
② http://127.0.0.1:8080/rules/add<br>
③ http://127.0.0.1:8080/rules/del<br>
④ http://127.0.0.1:8080/rules/modify<br>

注意：规则文件args.rule在项目一级目录下，在新环境配置时需要修改com/king/learning/constants/Config.java中的参数。<br>
与前端页面的接口如下示（Json格式）：<br>

## 1 显示数据
应用场景：用户进入首页后，首先展示的页面。<br>
请求url地址：①<br>
请求参数：无<br>
返回参数如下：<br>

    {   num:5,
        [
            {id:1, content:\.\./},
            {id:2, content:base64\(},
            {id:3, content:\:\$},
            {id:4, content:\$\},
            {id:5, content:xwork\.MethodAccessor},
        ]
    }
## 2 添加数据
应用场景：用户点击添加按钮弹出新表单，该表单可扩展性添加多条数据。<br>
请求url地址：②<br>
请求参数如下：<br>

    {   num:2,
        [
            {id:1，content:(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/},
            {id:2, content:\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\[},
        ]
    }
返回参数如下：<br>

    {   num:7,
        [
            {id:1, content:\.\./},
            {id:2, content:base64\(},
            {id:3, content:\:\$},
            {id:4, content:\$\},
            {id:5, content:xwork\.MethodAccessor},
            {id:6, content:(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/},
            {id:7, content:\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\[},
        ]
    }

## 3 删除数据
应用场景：用户选中打算删除的规则，点击删除按钮后提交规则id号。<br>
请求url地址：③<br>
请求参数如下：<br>

    {   num:2,
        [
            id:1，
            id:4,
        ]
    }
返回参数如下：<br>

    {   num:3,
        [
            {id:1, content:base64\(},
            {id:2, content:\:\$},
            {id:3, content:xwork\.MethodAccessor},
        ]
    }

## 4 修改数据
应用场景：用户选中打算修改的规则并点击修改按钮，在弹出的新表单中填写修改后的内容，完成修改后提交。<br>
请求url地址：④<br>
请求参数如下：<br>

    {   num:2,
        [
            {id:1，content:(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/},
            {id:3, content:\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\[},
        ]
    }
返回参数如下：<br>

    {   num:3,
        [
            {id:1, content:(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/},
            {id:2, content:\:\$},
            {id:3, content:\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\[},
        ]
    }

## 接口演示
如下图所示：<br>
![all_in_one](https://i.imgur.com/4epVAnm.gif)