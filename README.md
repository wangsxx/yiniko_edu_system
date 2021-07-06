# yiniko_edu_system

类似于慕课网一样的在线教育网站，能够进行课程的销售、在线播放等功能。主要包括系统前台(用户)、系统后台(管理员)和一套微服务后端接口。

## 微服务模块

+ common：通用的服务模块
  + common_utils：通用配置模块，如jwt工具类、MD5加密工具类、统一返回结果类等
  + service_base：通用服务配置模块，如Swagger配置类、Redis配置类等
+ service：主要微服务模块
  + service_cms：banner管理模块
  + service_msm：短信管理模块
  + service_edu：核心模块。课程、讲师等管理模块
  + service_order：订单管理模块，包括微信支付
  + service_oss：阿里云oss管理模块，如用户和讲师头像、课程封面图片等管理
  + service_user：用户管理模块、包括用户登录注册、微信扫描登陆
  + service_video：阿里云视频点播服务，课程视频在线播放等

## 主要功能

### 系统后台

+ 讲师管理模块
+ 课程分类管理模块
+ 课程管理模块
+ 订单管理
+ 首页banner管理

### 系统前台

+ 首页数据+banner显示
+ 讲师列表和详情
+ 课程列表和详情，包括课程视频播放
+ 登录和注册
+ 微信扫描登录
+ 微信扫描支付

## 技术栈

### 后端

+ 微服务架构：SpringBoot + Springcloud（OpenFeign、Hystrix、Nacos）
+ 数据库：MybatisPlus + Redis （首页热门数据缓存、短信缓存）
+ 路由转发和负载均衡：Nginx
+ Excel读写操作：easyExcel
+ 微信登录支付相关：jwt、OAuth2

### 前端

Vue、Nuxt、Element-UI

#### 其他

阿里云OSS、阿里云视频点播服务、微信登录和支付、Jenkins部署

## 环境要求

+ 微服务相关：jdk10、springboot2.3.5、springcloud Hoxton.SR9、springcloud alibaba 2.2.3
+ 数据库相关：mysql5.6、Redis6.0.9
+ 其他：Nginx1.19.6、nacos1.4、nodejs12.18.3

## [实现效果](https://github.com/wangsxx/yiniko_edu_system/tree/master/img)



