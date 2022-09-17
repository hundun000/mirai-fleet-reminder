### 定时提醒功能模块

### 【定时】定时发送消息（用于报时/提醒）

定时触发。报时文本来自配置文件。

>  <- 现在是周日晚上10点。请博士记得完成本周剿灭作战。

配置方法：

手动编辑（重启后生效）`data\具体插件id\ReminderFunction\repositories\ReminderListRepository.json`

```
{
  "SINGLETON" : {
    "items" : [ ... ]
    }
}
```

items中每一个元素是一个[ReminderItem结构](./ReminderFunction-base.md)

#### 【指令-Debug级】list

**<主指令名>: 代入 <角色名>ReminderFunctionDebug**  
**<子指令>: list**  

简单打印报时配置。

> -> /<主指令名> list 
> <- items:  
>    id:0    ReminderItem(count=null, reminderMessageCodes=[九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？, IMAGE:九点_0.png|九点_1.png, AUDIO:阿米娅_交谈2.amr], cron=* 0 9 * * ?)  
>    id:1    ReminderItem(count=null, reminderMessageCodes=[十点到了。欸嘿嘿......], cron=* 0 10 * * ?)  
>    id:2    ReminderItem(count=null, reminderMessageCodes=[现在是周日晚上10点。请博士记得完成本周剿灭作战。], cron=* 0 22 ? * 1)  

#### 【指令-Debug级】clockArrive

**<主指令名>: 代入 <角色名>ReminderFunctionDebug**  
**<子指令>: clockArrive**  
**<指令参数1>: 模拟的时间点。格式yyyy年M月d日H时m分**  

模拟到了某个时刻，测试插件的反应。（此时和正常情况下到时间一样，消息会发送给所有`启用`状态的群，而不是回复到Console里）

> -> /<主指令名> clockArrive 2022年1月1日9时0分  
> <- 九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？

