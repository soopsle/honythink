项目架构：
spring boot    项目基础架构，由springmvc+mybatis优化而来，可作为microservice前提
mybatis+mysql+druid  实现持久层功能
redis 
logback 实现api访问报文以及错误日志记录
actuator 项目基本信息监控
#docker  项目持续交付  待定

http://projects.spring.io/spring-boot/
Spring官方网站本身使用spring框架开发，随着功能以及业务逻辑的日益复杂，应用伴随着大量的XML配置文件以及复杂的Bean依赖关系。随着Spring 3.0的发布，Spring IO团队逐渐开始摆脱XML配置文件，并且在开发过程中大量使用“约定优先配置”（convention over configuration）的思想来摆脱Spring框架中各类繁复纷杂的配置（即时是Java Config）。
Spring Boot正是在这样的一个背景下被抽象出来的开发框架，它本身并不提供Spring框架的核心特性以及扩展功能，只是用于快速、敏捷地开发新一代基于Spring框架的应用程序。也就是说，它并不是用来替代Spring的解决方案，而是和Spring框架紧密结合用于提升Spring开发者体验的工具。同时它集成了大量常用的第三方库配置（例如Jackson, JDBC, Mongo, Redis, Mail等等），Spring Boot应用中这些第三方库几乎可以零配置的开箱即用（out-of-the-box），大部分的Spring Boot应用都只需要非常少量的配置代码，开发者能够更加专注于业务逻辑。


/***************************项目备注***************************************/
1 安装myeclipse
2 安装maven
3 安装mysql 创建数据库 honythink  空数据库即可
4 启动 
5 http://localhost/honythink/index





