# z-helper
基础工具包
 1. z-helper-exception
 2. z-helper-mulit-database
 3. z-helper-quartz
 4. z-helper-redis
 5. z-helper-swagger
 6. z-helper-web
## z-helper-exception
基础异常包
```java
/**
 * 基础错误码枚举，实现IErrorCode接口，框架内对错误的拓展都需要实现该接口，toString方法必须声明，内容参照此类
 */
public enum ErrorCode implements IErrorCode {...}
```
```java
/**
 * 框架包装异常类，继承RuntimeException，内部维护了一个IErrorCode，提供多个静态方法进行对错误码的包装
 */
public class HelperException extends RuntimeException {...}
```
```java
/**
 * 标记类型接口，标记错误码类型，定义了必须实现的错误码方法
 */
public interface IErrorCode {...}
```
## z-helper-mulit-database
多数据源配置，集成alibaba.druid
```java
/**
 * 方法注解，标记方法执行前替换数据源，方法执行后滞空数据源，所以使用了多数据源时要对所有方法进行注解，这里可以考虑优化
 */
public @interface SwitchDB {...}
```
## z-helper-quartz
调度任务基础包，集成Quartz
```java
/**
 * 任务相关错误码，实现IErrorCode接口，维护Quartz错误码
 */
public enum QuartzError implements IErrorCode {...}
```
```java
/**
 * Quartz操作类，内部维护一个Scheduler单例，提供一些基础的实例操作
 */
public class QuartzUtils {...}
```
```
提供一些常用到的Quartz配置，可视情况进行修改
quartz.properties
```
## z-helper-redis
Redis基础包，集成spring-boot-starter-data-redis
```java
/**
 * Redis错误码，实现IErrorCode接口，
 */
public enum RedisError implements IErrorCode {...}
```
```java
/**
 * Redis工具包,内部维护一个RedisTemplate单例，提供主要的操作
 */
public class RedisUtil {...}
```
## z-helper-swagger
Swagger基础包，集成springfox-swagger
```java
/**
 * SwaggerConfig 配置，采用@Configuration注解进行，所以使用时需要配合springboot进行扫描
 */
public class SwaggerConfig {...}
```
## z-helper-web
Web基础包，提供对web工程的基础支撑
```java
/**
 * 对控制层的全局切面，打印接口响应时间，捕获顶层异常由统一包装返回
 */
public class ControllerAOP {...}
```
```java
/**
 * 跨域配置，同理需要Spring扫描
 */
@Configuration
public class CrossConfig {...}
```
```java
/**
 * 统一的返回包装类
 */
public class ResponseEntity<T> {...}
```