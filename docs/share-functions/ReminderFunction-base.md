### ReminderItem结构说明

样例：

```
{
  "count" : null,
  "reminderMessageCodes" : [ "九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？", "IMAGE:九点_0.png|九点_1.png", "AUDIO:阿米娅_交谈2.amr" ],
  "cron" : "0 0 9 * * ?"
}
```

### count字段

执行次数条件。无限次对应null。

### reminderMessageCodes字段

一个数组。数组里每个元素按照ReminderMessageCode规则解读。bot届时将依次发送每一个元素。

ReminderMessageCode规则：

- "IMAGE:name.jpg" 将发送`data\具体插件id\ReminderFunction\images\name.jpg`
- "AUDIO:name.amr" 将发送`data\具体插件id\ReminderFunction\audios\name.amr`
- 其他文本内容，将发送为文本

更多用法：

- "IMAGE:name\_0.png|name\_1.png" 随机发送`name\_0.png`或`name\_1.png`中的一个

样例：

```
// 届时将依次发送 文本 + 图片 + 音频
"reminderMessageCodes" : [ "九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？", "IMAGE:九点.png", "AUDIO:阿米娅_交谈2.amr" ]

// 届时将发送 文本
"reminderMessageCodes" : [ "十点到了。欸嘿嘿......" ]
```

### cron字段

执行的时间条件。一个cron表达式，只支持秒条件填`0`。

附：[cron表达式在线编辑器](https://www.bejson.com/othertools/cron/)

【注意】插件只会每分钟（的某一秒）检查一次当前分钟第0秒的提醒任务，所以cron表达式只支持秒条件填`0`。例如插件可能在`09:00:42`检查，此时检查的是提醒任务的cron是否满足`09:00:00`。

【注意】本项目的cron实现使用quartz库，某些语法可能和在线编辑器不同，如有遇到表达式行为不符合预期，请自行按照 [quartz文档](https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm)为准核对表达式。

样例：

```
// 表示每天的09:00:00 (插件会在09:00的某一秒执行)
"cron" : "0 0 9 * * ?"

// 表示每周日的22:00:00 (插件会在22:00的某一秒执行)
"cron" : "0 0 22 ? * SUN"
```
