### 定时提醒功能模块

### 【定时】定时报时

定时触发。报时文本来自配置文件。

>  <- 九点到了。罗德岛全舰正处于通常航行状态。博士，整理下航程信息吧？

配置方法：

手动编辑（重启后生效）`config\具体插件id\ReminderFunction\repositories\ReminderListRepository.json`

```
{
  "SINGLETON" : {
    "items" : [ ... ]
    }
}
```

items中每一个元素是一个[ReminderItem结构](./ReminderFunction-base.md)