### ReminderItem结构说明

样例：

```
{
  "count" : null,
  "reminderMessageCodes" : [ "九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？", "九点_0.png|九点_1.png", "AUDIO:阿米娅_交谈2.amr" ],
  "cron" : "* 0 9 * * ?"
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

执行的时间条件。一个 [cron表达式](https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm)。

【注意】插件只会每分钟（的某一个毫秒）检查一次提醒任务，所以cron表达式中的毫秒条件只应填`*`

【注意】本项目的cron实现使用quartz库。特别地，其对于星期几的数值表示和某些其他标准中的表示不同，注意不要混淆。例如周日的的数值表示是1（而不是某些其他标准中的0或7）。具体如下，

```
("SUN", 1);
("MON", 2);
("TUE", 3);
("WED", 4);
("THU", 5);
("FRI", 6);
("SAT", 7);
```

样例：

```
// 表示09:00AM
"cron" : "* 0 9 * * ?"

// 表示每周日的10:00PM
"cron" : "* 0 22 ? * 1"
```
