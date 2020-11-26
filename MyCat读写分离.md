## 	下载mycat

> [| MYCAT官方网站—开源分布式数据库中间件](http://www.mycat.org.cn/)

![image-20201123200400596](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/blob/main/typora-user-images/image-20201123200400596.png?raw=true)

![image-20201123200454433](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123200454433.png?raw=true)

## 	安装mycat

![image-20201123200904318](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123200904318.png?raw=true)

```shell
tar -zxvf Mycat-server-1.6.7.4-release-20200105164103-linux.tar.gz
```

![image-20201123201339669](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123201339669.png?raw=true)



``` shell
cp -r mycat /usr/local
```



![image-20201123201836591](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123201836591.png?raw=true)



![image-20201123202458797](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123202458797.png?raw=true)

```shell 
cd  /usr/local/mycat/conf
```



ll 查看conf里面的配置文件

![image-20201123210054989](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123210054989.png?raw=true)



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



![image-20201123205146997](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123205146997.png?raw=true)

```shell 
mysql -uroot -pChenyingge_444 -h 192.168.133.130 -P 3306
```



![image-20201123205410732](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123205410732.png?raw=true)

```shell
cd bin
```



![image-20201123205821410](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123205821410.png?raw=true)

```shell
./mycat start
```



![image-20201123210313811](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123210313811.png?raw=true)



调试人员端口9066

```shell	
mysql -uroot -pChenyingge_444 -P 9066 -h 192.168.133.128
```

![image-20201123211622366](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123211622366.png?raw=true)



使用root用户登录

```shell	
mysql -uroot -pChenyingge_444 -P 8066 -h 192.168.133.128
```



![image-20201123212643580](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123212643580.png?raw=true)



进入数据库

```sql
use mycat_testdb
```



![image-20201123230257464](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123230257464.png?raw=true)

查看表

```sql
select * from mytbl;
```



![image-20201123230345931](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123230345931.png?raw=true)

修改表

```sql
insert into mytbl values(3,'wangwu');
```



![image-20201123230623072](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201123230623072.png?raw=true)



以上说明已完成root用户的读写测试







使用user用户登录

```shell	
mysql -uroot -pChenyingge_444 -P 8066 -h 192.168.133.128
```

![image-20201126181004138](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201126181004138.png?raw=true)

查看表内情况

![image-20201126181104702](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201126181104702.png?raw=true)

​                                                                                   可以读到之前root用户修改后的数据



尝试修改表中数据

```sql
insert into mytbl values(4,'xiaoming');
```



![image-20201126182023688](https://github.com/MonkeyCookster/MarkdownPhotos-Repository/tree/main/typora-user-images/image-20201126182023688.png?raw=true)

​                                     报错user只有读权限

以上说明已完成user用户的读写测试



mycat读写分离功能已实现
