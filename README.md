# 使用介绍

这是一个快速的 SpringBoot 启动应用。

- 使用了 SpringBoot 作为 MVC 框架
- 使用了 Swagger 作为实时文档，访问 localhost:{port}/swagger-ui.html 即可访问
- 使用了 Druid 作为数据库连接池
- 使用了 fastjson 作为 json 序列化库
- 使用了 mybatis 作为 orm 框架
- 使用了 mybatis-pagehelper 分页
- 使用了 mybatis-plus 作为通用mapper
- 使用了 redis 作为缓存支持
- 使用了 FreeMarker 作为模板生成引擎（用来生成代码）


## 快速启动

1. 需要先利用 test/java/CodeGenerator.java 来快速生成代码，输入数据库信息，输入表名，即可生成代码

```java
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "password";

    ....

    public static void main(String[] args) {
        genCode("输入表名");
        //genCodeByCustomModelName("输入表名","输入自定义Model名称");
    }
```

2. 运行 Application.java 的 main 方法即可。

## 详细配置

**errorcode 配置**

错误码配置在 resource/errorcode.properties ，文件中有一个案例 `10001=失败` , 再在 BizErrorCode.java 中填写 `public static final int FAIL = 10001;` 即可制定一个错误码。

通过 `ErrorCodeHelper.get(BizErrorCode.FAIL)` 即可获取错误信息。

**version 配置**

版本配置文件在 resource/version.properties ，通过在 maven 的 pom.xml 中配置相关信息即可。



