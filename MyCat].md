# Mycat数据库中间件
## 一、什么是MyCat：

MyCat是一个开源的分布式数据库系统，是一个实现了MySQL协议的服务器，前端用户可以把它看作是一个数据库代理，用MySQL客户端工具和命令行访问，而其后端可以用MySQL原生协议与多个MySQL服务器通信，也可以用JDBC协议与大多数主流数据库服务器通信，其核心功能是分表分库，即将一个大表水平分割为N个小表，存储在后端MySQL服务器里或者其他数据库里。

## 二、为什么要用Mycat

MyCat发展到目前的版本，已经不是一个单纯的MySQL代理了，它的后端可以支持MySQL、SQL Server、Oracle、DB2、PostgreSQL等主流数据库，也支持MongoDB这种新型NoSQL方式的存储，未来还会支持更多类型的存储。而在最终用户看来，无论是那种存储方式，在MyCat里，都是一个传统的数据库表，支持标准的SQL语句进行数据的操作，这样一来，对前端业务系统来说，可以大幅降低开发难度，提升开发速度

## 三、Mycat原理

MyCat技术原理中最重要的一个动词是“拦截”，它拦截了用户发送过来的SQL语句，首先对SQL语句做了一些特定的分析：如分片分析、路由分析、读写分离分析、缓存分析等，然后将此SQL发往后端的真实数据库，并将返回的结果做适当的处理，最终再返回给用户。



问题1：java程序与数据库紧耦合，一旦数据库挂掉，整个程序就不能正常运行

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124130957626.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)



为了解决这些问题，在Cobar的基础上开发了mycat

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124130910645.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


## 读写分离

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112413101397.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)

双主双从

## 数据分片

## 多数据源整合



mycat的读写分离是建立在两个及以上的数据库上实现的，那么两个数据库中的数据就要保持一致，这就要求我们熟知mysql是怎样实现主从复制的，以及如何保证数据的可靠性。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131053937.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)






# 准备工作

## 1.安装虚拟机CentOs7

​	因为mycat是运行在linux环境下的，所以我们要去安装linux环境。	

​	这里选择CentOS（Community Enterprise Operating System，中文意思是社区企业操作系统）是Linux发行版之一，它是来自于Red Hat Enterprise Linux依照开放源代码规定释出的源代码所编译而成。由于出自同样的源代码，因此有些要求高度稳定性的<u>**服务器**</u>以CentOS替代商业版的Red Hat Enterprise Linux使用。两者的不同，在于CentOS完全 <u>**开源**</u>。

​	

下载地址：http://202.115.195.254/Default.aspx?SubDir=\2+软件资料\操作系统\Cent+OS+7

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131120491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)
)]



然后安装CentOs：（不会的同学参考以下操作）

https://blog.csdn.net/u013168176/article/details/81144193?ops_request_misc=%7B%22request%5Fid%22%3A%22160612607619725222414630%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=160612607619725222414630&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-1-81144193.first_rank_ecpm_v3_pc_rank_v2&utm_term=centos安装&spm=1018.2118.3001.4449

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131211697.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)



配好虚拟机后要安装vim

```shell
yum -y install vim*
```

linux与windows是不能直接相互传东西的，为了方便我们将来修改mysql以及mycat的配置文件，我们需要安装以下两个工具：

xshell与xftp



## 2.xshell与xftp的安装及作用


### Xshell 

 是一个强大的安全终端模拟[软件](https://baike.baidu.com/item/软件/12053)，它支持SSH1, SSH2, 以及Microsoft Windows 平台的TELNET 协议。Xshell 通过互联网到远程[主机](https://baike.baidu.com/item/主机/455151)的安全连接以及它创新性的设计和特色帮助用户在复杂的网络环境中享受他们的工作。

Xshell可以在Windows界面下用来访问远端不同系统下的服务器，从而比较好的达到远程控制终端的目的。除此之外，其还有丰富的外观配色方案以及样式选择。



下载地址：https://www.netsarang.com/en/downloading/

xshell的作用是你可以将windows下的指令复制到centos中的shell里执行

xftp的作用是你可以将windows下的文件直接拖到linux操作系统下的文件夹中，可以省去很多命令，比较方便，且用不来vim的同学也可以在windows下配置好再发送到linux下。

新建一个会话：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131327554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


如何获取虚拟机的ip
获取命令：

```shell
ip addr
```



![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131359613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131500107.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)

然后就可以在xshell中操作centos了





### xftp的使用：

与xshell建立连接类似，连接后我们可以看到centos下的目录文件，间接使用centos的图形化界面，我们在安装mycat的时候要先去官网上下载安装包，然后解压到centos 下的制定目录文件下，此时只需要在windows下解压好，然后拖到指定的目录下即可，不再需要输入各种命令行。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131544507.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)




## 3.CentOs7下mysql的安装与配置

推荐博文一：

https://blog.csdn.net/weixin_42140801/article/details/107859925

推荐博文二：

https://blog.csdn.net/qq_42376889/article/details/105537454

### 安装

下载mysql之前需要先在yum仓库中添加mysql-server rpm包

```shell
wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm
```



​	利用yum来安装mysql的命令：

```shell
 yum -y install mysql57-community-release-el7-10.noarch.rpm
```



![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131626924.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)




安装mysql服务器

```shell
 yum -y install mysql-community-server
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131642312.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)




### 配置

#### 账号密码配置：

```shell
启动mysql
systemctl start mysqld.service
查看mysql状态
systemctl status mysqld.service
获取初始密码
grep "password" /var/log/mysqld.log
登录mysql
mysql -uroot -p+初始密码
修改密码
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new password';
```




![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131701846.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)




#### 开启mysql的远程访问

由于mycat需要对多机mysql进行操作，这里需要开启mysql的远程访问以及关闭防火墙。

```shell
grant all privileges on *.* to 'root'@'%' identified by 'Password_1' with grant option;
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131717788.png#pic_center)


#### 关闭掉防火墙

```shell
systemctl stop firewalld
```

以上所有操作以后，就配好一台虚拟机的mysql，最少要配置三台虚拟机才能实现mycat运用场景的要求。


# 主从复制操作

#### 1.修改配置文件：

```shell
vim /etc/my.cnf
#主服务器唯一ID
server-id=1
#启用二进制日志
log-bin=mysql-bin
# 设置不要复制的数据库(可设置多个)
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
#设置需要复制的数据库
binlog-do-db=需要复制的主数据库名字
#设置logbin格式
binlog_format=STATEMENT
```

![image-20201126180228293](https://img-blog.csdnimg.cn/20201126192655336.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L09yYW5nZVNhbmE=,size_16,color_FFFFFF,t_70#pic_center)

####  2.从机配置(host80)：

```shell
修改配置文件：vim /etc/my.cnf
#从服务器唯一ID
server-id=2
#启用中继日志
relay-log=mysql-relay
```

#### 3.主机、从机重启 MySQL 服务

#### 4.主机从机都关闭防火墙

#### 5.在主机上建立帐户并授权 slave

```shell
#在主机MySQL里执行授权命令
GRANT REPLICATION SLAVE ON *.* TO 'slave'@'%' IDENTIFIED BY '123123';
#查询master的状态
show master statu;
```

![image-20201126180505267](https://img-blog.csdnimg.cn/20201126192709115.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L09yYW5nZVNhbmE=,size_16,color_FFFFFF,t_70#pic_center)

#### 6.在从机上配置需要复制的主机

```shell
#复制主机的命令
CHANGE MASTER TO MASTER_HOST='主机的IP地址',
MASTER_USER='slave',
MASTER_PASSWORD='123123',
MASTER_LOG_FILE='mysql-bin.具体数字',MASTER_LOG_POS=具体值;
#启动从服务器复制功能
start slave;
#查看从服务器状态
show slave status\G;
#下面两个参数都是Yes，则说明主从配置成功！
# Slave_IO_Running: Yes
# Slave_SQL_Running: Yes
```

![image-20201126180624411](https://img-blog.csdnimg.cn/20201126192714549.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L09yYW5nZVNhbmE=,size_16,color_FFFFFF,t_70#pic_center)

#### 7.主机新建库、新建表、insert 记录，从机复制

![image-20201126180708489](https://img-blog.csdnimg.cn/20201126192722271.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L09yYW5nZVNhbmE=,size_16,color_FFFFFF,t_70#pic_center)

#### 8.如何停止从服务复制功能

```shell
stop slave;
```

#### 9.如何重新配置主从

```shell
stop slave;
reset master;
```


## 4.mycat的安装与配置

linux系统安装软件的方式有四种：


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131743715.png#pic_center)




mycat属于解压后即可使用的一类软件。

第一步：下载mycat安装包

http://www.mycat.org.cn/

选择自己想要的版本

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201124131800801.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


然后通过xftp将mycat的安装包拖到 /opt目录下（此目录下一般是放安装包）

解压后递归复制到 /user/local目录下（安装软件默认在这个目录下）

```shell
tar -zxvf Mycat-server-1.6.7.4-release-20200105164103-linux.tar
```

```shell
cp -r mycat /user/local/
```





![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112413181681.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


1.从机在请求复制主机文件时，主机会对从机的**<u>权限进行判断</u>**。

2.主机会将现节点关于数据操作的sql写到一个二进制的日志文件中，这个binlog就是从机里的数据来源

3.拥有权限的从机就读取binlog，然后将binlog写到自己的中继日志relaylog里

4.从机再读取自己的中继日志里的sql语句并执行，这样就把数据复制过来了。



特点：mysql是从接入点开始复制内容，在这个过程中有两次I/O操作哦，这样就会存在延时性的问题。

什么是延时性的问题：用户在注册一个账号时，数据库会把账号注册时间也存下来，需要在sql语句中有获取当前系统时间并存入数据库这样的操作，这条语句到从机执行时就会比主机慢。

如何解决：这里需要了解binlog日志的三种格式

```shell
binlog_format =STATEMENT
```



​	1.statement

​		把所有的写操作写到binlog中，复制到从机里，但是在复制时，时间还是各自的系统时间，会造成数据不一致

​	2.row

​		记录每一行的数据，但是在主机跟新全表时候，从机就要同时执行，效率比较低

​	3.mixED

下来，需要在sql语句中有获取当前系统时间并存入数据库这样的操作，这条语句到从机执行时就会比主机慢。

如何解决：这里需要了解binlog日志的三种格式

```shell
binlog_format =STATEMENT
```

# mycat读写分离

## 	下载mycat

> [| MYCAT官方网站—开源分布式数据库中间件](http://www.mycat.org.cn/)
> 
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192602975.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192648977.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


## 	安装mycat
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192839987.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)
```shell
tar -zxvf Mycat-server-1.6.7.4-release-20200105164103-linux.tar.gz
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192904376.png#pic_center)

```shell
cp -r mycat /usr/local
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192925937.png#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126192943389.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)

```shell 
cd  /usr/local/mycat/conf
```



ll 查看conf里面的配置文件
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193013780.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)



## 配置mycat

配置schema.xml

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
    <!-- TESTDB1 是mycat的逻辑库名称，链接需要用的 -->
    <schema name="mycat_testdb" checkSQLschema="false" sqlMaxLimit="100" dataNode="dn1"></schema>
	<!--  checkSQLschema 当该值为true时，例如我们执行语句select * from TESTDB.company 。mycat会把语句修改为 select * from company 去掉TESTDB。
		  sqlMaxLimit 当该值设置为某个数值时，每条执行的sql语句，如果没有加上limit语句，Mycat会自动加上对应的值。不写的话，默认返回所有的值。
	-->
        <!-- database 是MySQL数据库的库名 -->
    <dataNode name="dn1" dataHost="localhost1" database="test" />
    <!--
    dataHost 节点中各属性说明：
        name：物理主机节点名称；
        maxCon：指定物理主机服务最大支持1000个连接；
        minCon：指定物理主机服务最小保持10个连接；
		balance：指定物理主机服务的负载模式。
            0，不开启读写分离机制；
            1，全部的readHost与stand by writeHost参与select语句的负载均衡，简单的说，当双主双从模式(M1->S1，M2->S2，并且M1与 M2互为主备)，正常情况下，M2,S1,S2都参与select语句的负载均衡；
            2，所有的readHost与writeHost都参与select语句的负载均衡，也就是说，当系统的写操作压力不大的情况下，所有主机都可以承担负载均衡；
			3，所有读请求随机的分发到wiriterHost对应的readhost执行，writerHost不负担读压力，注意balance=3只在1.4版本后有，1.3版本没有
        writeType：指定写入类型；
            0，只在writeHost节点写入；
            1，在所有节点都写入。慎重开启，多节点写入顺序为默认写入根据配置顺序，第一个挂掉切换另一个；
        dbType：指定数据库类型；
        dbDriver：指定数据库驱动；
		switchType: 0 不自动切换
					1 默认值 自动切换
					2 基于MySql主从同步的状态决定是否切换
		switchType="2" 与slaveThreshold="100"，此时意味着开启MySQL主从复制状态绑定的读写分离与切换机制。
		心跳机制是定时发送一个自定义的结构体(心跳包)，让对方知道自己还活着，以确保连接的有效性的机制。
        
-->
    <dataHost name="localhost1" maxCon="1000" minCon="10" balance="3" writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
        <heartbeat>select user()</heartbeat>
        <!-- 可以配置多个主从 -->
        <writeHost host="hostM1" url="192.168.133.131:3306" user="root" password="Chenyingge_444">
            <!-- 可以配置多个从库 -->
            <readHost host="hostS2" url="192.168.133.130:3306" user="root" password="Chenyingge_444" />
        </writeHost>
    </dataHost>
</mycat:schema>
```



配置server.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mycat:server SYSTEM "server.dtd">
<mycat:server xmlns:mycat="http://io.mycat/">
   

   <!-- 读写都可用的用户 -->
    <user name="root" defaultAccount="true">
        <property name="password">Chenyingge_444</property>
        <property name="schemas">mycat_testdb</property>
    </user>

    <!-- 只读用户 -->
    <user name="user">
        <property name="password">Chenyingge_444</property>
        <property name="schemas">mycat_testdb</property>
        <property name="readOnly">true</property>
    </user>

</mycat:server>
```



## 启动mycat

验证是否可以远程连接数据库



启动sql服务 

```shell 
/bin/systemctl start mysqld.service
```



验证是否可以远程连接数据库

```shell 
mysql -uroot -pChenyingge_444 -h 192.168.133.131 -P 3306
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193035440.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)

```shell 
mysql -uroot -pChenyingge_444 -h 192.168.133.130 -P 3306
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193054227.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


```shell
cd bin
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193111426.png#pic_center)

```shell
./mycat start
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193134691.png#pic_center)




调试人员端口9066

```shell	
mysql -uroot -pChenyingge_444 -P 9066 -h 192.168.133.128
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193200233.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)



使用root用户登录

```shell	
mysql -uroot -pChenyingge_444 -P 8066 -h 192.168.133.128
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112619324472.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)




进入数据库

```sql
use mycat_testdb
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193309161.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)

查看表

```sql
select * from mytbl;
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193342943.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


修改表

```sql
insert into mytbl values(3,'wangwu');
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193401396.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)



以上说明已完成root用户的读写测试







使用user用户登录

```shell	
mysql -uroot -pChenyingge_444 -P 8066 -h 192.168.133.128
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193432984.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


查看表内情况
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193454952.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lva25h,size_16,color_FFFFFF,t_70#pic_center)


​                                                                                   可以读到之前root用户修改后的数据



尝试修改表中数据

```sql
insert into mytbl values(4,'xiaoming');
```


![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193508801.png#pic_center)

​                                     报错user只有读权限

以上说明已完成user用户的读写测试



mycat读写分离功能已实现
​	
 # springboot整合mycat实现读写分离

##### 1.新建springboot项目，选择依赖

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193924206.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)

##### 2.在pom.xml中添加如下内容：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126193947849.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)

##### 3.新建generatorConfig.xml文件，利用mybatis的逆向工程：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126194009518.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)

##### 4.在application.properties中配置数据库信息：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112619402383.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)

登录的是mycat的root用户

##### 5.编写service层方法：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126194033215.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)

##### 6.编写SpbootmycatApplication如下，启动后即可查看读写分离效果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201126194043366.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FpcnljaG8=,size_16,color_FFFFFF,t_70#pic_center)
