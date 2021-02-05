# JDBC Template

## JDBC 概念

Spring对于JDBC的封装，使用JDBC Template可以更加方便的实现对于数据库的增删改查操作。

## 准备工作

引入相关的依赖

配置连接池

配置JDBCTemplate对象，注入DataSoruce

创建Service与DAO，在DAO进行注入JDBCTemplate对象，在Service注入DAO