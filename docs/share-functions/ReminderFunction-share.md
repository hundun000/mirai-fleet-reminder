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

#### 【指令】查看报时配置

**<子指令>: 查询报时**  

简单打印报时配置，仅作为调试。

> -> /<主指令名> 查询报时  
> <- items:  
>    id:0    ReminderItem(count=null, reminderMessageCodes=[九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？, IMAGE:九点_0.png|九点_1.png, AUDIO:阿米娅_交谈2.amr], cron=* 0 9 * * ?)  
>    id:1    ReminderItem(count=null, reminderMessageCodes=[十点到了。欸嘿嘿......], cron=* 0 10 * * ?)  
>    id:2    ReminderItem(count=null, reminderMessageCodes=[现在是周日晚上10点。请博士记得完成本周剿灭作战。], cron=* 0 22 ? * 1)  

#### 【Console指令】测试定时任务

仅限Console环境使用。

**<子指令>: debugClockArrive**  
**<指令参数1>: 模拟的时间点。格式yyyy年M月d日H时m分**  

模拟到了某个时刻，测试插件的反应。（此时和正常情况下到时间一样，消息会发送给所有`启用`状态的群，而不是回复到Console里）

> -> /<主指令名> debugClockArrive 2022年1月1日9时0分  
> <- 九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？

<! -- 【以注释的方式隐藏该部分】

#### 【指令】创建提醒任务

**<子指令>: 创建提醒**  
**<指令参数1>: 时间条件。用~代替cron表达式中的空格**  
**<指令参数2>: 执行次数条件。值域：x次，无限次**  
**<指令参数3>: 提醒内容。即bot会发送的文本**  

>  -> /<主指令名> 创建提醒 \*\~0\~22\~?\~\*\~1 无限次 现在是周日晚上10点。请博士记得完成本周剿灭作战。  
>  <- OK

【注意】插件只会每分钟（的某一个毫秒）检查一次提醒任务，所以cron表达式中的毫秒条件只应填`*`。

#### 【指令】查看所有提醒任务

**<子指令>: 查询提醒**  

>  -> /<主指令名> 查询提醒  
>  <- items:  
>     id:0  ReminderItem(count=null, reminderMessageCodes=[现在是周日晚上10点。请博士记得完成本周剿灭作战。,IMAGE:name.jpg], cron=* 0 22 ? * 7)

#### 【指令】删除提醒任务

**<子指令>: 删除提醒**  
**<指令参数1>: id。即查询提醒里看到的id**

>  -> /<主指令名> 删除提醒 0  
>  <- OK

-->
