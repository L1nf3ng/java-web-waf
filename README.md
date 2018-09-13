# java-web-waf
A web GUI to configure waf based on nginx-lua module

**更新说明**<br>
加入分类字段，从而实现按大类的增删改查，当然也需要按照类别展示给用户。<br>

**效果图**<br>
大致如下图，有一个类似菜单的分类功能。每一页可以显示、增加、删除、修改规则。最后有一个部署功能的接口。<br>
![fdsrweq](https://i.imgur.com/Gc0G6dr.jpg)

简单的基于磁盘文件的增删改<br>
目前已经完成的接口有：<br>

① http://127.0.0.1:8080/rules/<br>
② http://127.0.0.1:8080/rules/select<br>
③ http://127.0.0.1:8080/rules/add<br>
④ http://127.0.0.1:8080/rules/del<br>
⑤ http://127.0.0.1:8080/rules/modify<br>

注意：规则文件在项目一级目录的rule-config下，在新环境配置时需要修改com/king/learning/constants/Config.java中的参数。<br>
与前端页面的接口如下示（Json格式）：<br>

## 1 获取元数据
应用场景：用户进入首页后，首先获得关于类别的信息。<br>
请求url地址：①<br>
请求参数：无<br>
返回参数如下：<br>

    {   "pageNum": 8,
        "nameList": {
            "1": "args",
            "2": "blackip",
            "3": "cookie",
            "4": "post",
            "5": "url",
            "6": "useragent",
            "7": "whiteip",
            "8": "whiteurl"
        }
    }

## 2 查询页面
应用场景：当用户点击某个大类时需要请求该接口，以获得该类别下的规则。<br>
请求url地址：②<br>
请求参数：

    {   "pageNo":7,
    }
返回参数如下：<br>

    {   "pageNo":7,
        "num":5,
        [
            {"id":1, "content":"\.\./"},
            {"id":2, "content":"base64\("},
            {"id":3, "content":"\:\$"},
            {"id":4, "content":"\$\"},
            {"id":5, "content":"xwork\.MethodAccessor"},
        ]
    }

## 3 添加数据
应用场景：用户点击添加按钮弹出新表单，该表单可扩展性添加多条数据。<br>
请求url地址：③<br>
请求参数如下：<br>

    {   "pageNo":7,
        "num":2,
        [
            {"id":1，"content":"(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/"},
            {"id":2, "content":"\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\["},
        ]
    }
返回参数如下：<br>

    {   "pageNo":7,
        "num":7,
        [
            {"id":1, "content":"\.\./"},
            {"id":2, "content":"base64\("},
            {"id":3, "content":"\:\$"},
            {"id":4, "content":"\$\"},
            {"id":5, "content":"xwork\.MethodAccessor"},
            {"id":6, "content":"(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/"},
            {"id":7, "content":"\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\["},
        ]
    }

## 4 删除数据
应用场景：用户选中打算删除的规则，点击删除按钮后提交规则id号。<br>
请求url地址：④<br>
请求参数如下：<br>

    {   "pageNo":7,
        "num":2,
        [
            "id":1，
            "id":4,
        ]
    }
返回参数如下：<br>

    {   "pageNo":7,
        "num":3,
        [
            {"id":1, "content":"base64\("},
            {"id":2, "content":"\:\$"},
            {"id":3, "content":"xwork\.MethodAccessor"},
        ]
    }

## 5 修改数据
应用场景：用户选中打算修改的规则并点击修改按钮，在弹出的新表单中填写修改后的内容，完成修改后提交。<br>
请求url地址：⑤<br>
请求参数如下：<br>

    {   "pageNo":7,
        "num":2,
        [
            {"id":1，"content":"(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/"},
            {"id":3, "content":"\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\["},
        ]
    }
返回参数如下：<br>

    {   "pageNo":7,
        "num":3,
        [
            {"id":1, "content":"(gopher|doc|php|glob|file|phar|zlib|ftp|ldap|dict|ogg|data)\:\/"},
            {"id":2, "content":"\:\$"},
            {"id":3, "content":"\$_(GET|post|cookie|files|session|env|phplib|GLOBALS|SERVER)\["},
        ]
    }

## 接口演示
如下图所示：<br>
![buglaong](https://i.imgur.com/94FDVns.gif)