> 对jpa的一层极简封装，不用再去构建复杂的spec去进行复杂查询了。极其轻量的组件，没有增加任何性能成本，0配置直接使用

## 目前仅完成大概的设计，具体实现尚需补充，欢迎提供意见和指教

### 使用：
1. 注解@EnableJpa 
2. repository 继承 BaseRepository

### 功能支持
* 条件查询
* 子查询
* sum查询
* 关联查询
* 分页查询

