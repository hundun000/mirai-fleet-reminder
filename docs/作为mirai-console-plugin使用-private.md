### 1. 环境准备

- java 11
- [mirai-console](https://github.com/mamoe/mirai/blob/dev/docs/UserManual.md)
- (可选)准备好[在聊天环境执行指令](https://github.com/project-mirai/chat-command)  

### 2. 下载本项目制品

- mirai-fleet-XXX-XXX.mirai.jar

插件本体，放入mirai-console的plugins文件夹。

- ConfigAndData.zip

解压后得到config、data合并至mirai-console的同名文件夹。若解压后没有config文件夹，说明该版本已不需要人工准备文件夹，插件会自动使用样例值创建。

### 3. 配置权限

本插件的指令受mirai-console权限系统管理，即权限配置结果会保存在`config/Console/PermissionService.yml`。

不是简单地将`hundun.fleet.reminder:*`授权给用户，而是有特殊用法，目的是：

- 定时触发的功能，可控制启用/禁用
- 若一个console里运行了两个bot，加入了同一个群，可控制仅其中一个bot响应本插件，另一个不响应本插件

**推荐的授权方法：**

使用`权限助手指令`(见下文)

因为这本身也是一种指令，所以调用者首先要拥有权限`hundun.fleet.reminder:command.定时提醒权限助手`。

- 若想在console内使用`权限助手指令`，此时一定符合权限要求，可以直接使用权限助手模块里的具体指令。

- 若想在聊天环境使用`权限助手指令`，则需[人工将上述权限授予某些对象](https://github.com/mamoe/mirai/blob/dev/docs/ConsoleTerminal.md#%E5%9C%A8%E7%BE%A4%E8%81%8A%E4%B8%AD%E4%BD%BF%E7%94%A8%E5%91%BD%E4%BB%A4-%E6%9D%83%E9%99%90%E6%8E%88%E4%BA%88)。

> 人工通过perm指令，将上述权限授予id为114514的用户。再加上已经备好“在聊天环境执行指令”，则id为114514的用户可以在聊天环境使用权限助手模块里的具体指令了。
>
> /perm permit u114514 hundun.fleet.reminder:command.定时提醒权限助手  

然后使用权限助手模块里的具体指令即可。

### 权限助手模块

本框架设计以（bot + 群）为单位管理权限，称为该bot对于该群的`群开关`。如启用，则该bot会定时向该群发送消息。

若一个console里运行了两个bot，加入了同一个群，可控制仅其中一个bot开启`群开关`，另一个不开启`群开关`

权限助手模块即是用于方便地控制`群开关`。

#### 【指令】控制群开关

**<子指令>: 群开关**   
**<指令参数1>: 是否启用**    
**<指令参数2>: 参数解析group**      

[参数解析group说明](https://github.com/mamoe/mirai/blob/dev/docs/ConsoleTerminal.md#%E6%8C%87%E4%BB%A4%E5%8F%82%E6%95%B0%E6%99%BA%E8%83%BD%E8%A7%A3%E6%9E%90)。此时需要对象bot在线。

> 将（bot 123456，群7891011）的`群开关`设为 启用
>
> -> /定时提醒权限助手 群开关 true 123456.7891011  
> <- 成功

#### 【console指令】控制群开关

仅限console环境使用。即使没有bot在线也可以使用。

**<子指令>: 群开关Console**  
**<指令参数1>: 是否启用**  
**<指令参数2>: bot id**  
**<指令参数3>: 群id**  
  

> 将（bot 123456，群7891011）的`群开关`设为 启用
>
> -> /定时提醒权限助手 群开关Console true 123456 7891011  
> <- 成功


### 4. 启动和登录

启动mirai-console，在mirai-console里登录。
