## v0.1.3

开发者测试时使用的mirai-console版本：2.12.3（建议用户不要落后太多版本）

### 对比0.1.2 

### 新特性

- cron表达式规则优化，使其更符合cron常用习惯。

可兼容旧版配置文件。对于cron写法，旧版`* 0 9 * * ?`和新版`0 0 9 * * ?`行为一致，均为“插件会在09:00的某一秒执行”。

### 修复

修复0.1.2：无法顺利配置随机内容

### ConfigAndData变化

无。

## v0.1.2

开发者测试时使用的mirai-console版本：2.12.3（建议用户不要落后太多版本）

### 对比0.1.1 

### 已知问题

- 无法顺利配置随机内容

### 新特性

- 新增提供若干debug类指令
- 支持配置发送随机内容

### 修复

修复0.1.1：需要额外config文件否则加载失败

### ConfigAndData变化

配合默认配置里发送随机内容，更新图片文件