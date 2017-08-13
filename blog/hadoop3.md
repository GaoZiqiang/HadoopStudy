# Hadoop系列－－Hadoop再安装
## **1 安装步骤简介**
### **1.1 前言**
　　讲真，上次的Hadoop的安装实际上是失败的，正应了那句话，Hadoop的学习是有一定的门槛的，从Hadoop的安装就可见一斑。

　　最近看了一些关于Hadoop的理论知识，对Hadoop的理解更进了一步，便决定重新安装一次Hadoop。
### **1.2 安装大体步骤**
　　因为Hadoop是Java语言开发的，所以必然需要Java的运行环境，那么就需要配置JDK，鉴于开发的效率，Linux系统环境是最理想的开发环境了，那么可以1配置Linux虚拟机或者2购买远程Linux主机或者3直接安装Linux系统；最后安装配置Hadoop即可。

　　总体来说，大体步骤如下：

 - 准备Hadoop系统环境
 - 配置JDK
 - 安装Hadoop
 - 配置Hadoop

## **2 安装步骤详解**
　　下面展开详解。
### **2.1 准备Hadoop系统环境**
　　按照上面说的，方法有三个：
　　

 - 配置Linux虚拟机
 - 购买远程Linux主机
 - 直接安装Linux系统

### **2.2 配置JDK**
**1.下载JDK**

**2.安装JDK**

**3.配置环境变量**

　　可以配置.bashrc和/etc/profile两个文件，但是.bashrc的优先级高于/etc/profile。

　　个人参照如下：
　　

```
  export JAVA_HOME=/home/gaoziqiang/devel/jdk/jdk_8/jdk1.8.0
  export JRE_HOME=$JAVA_HOME/jre                                                        
  export PATH=$JAVA_HOME/bin:JRE_HOME/bin:$PATH
  export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

```

### **2.3 安装Hadoop**
　　经过十多年的发展，Hadoop已经发布了ver1.x和ver2.x两套版本，ver1.x比较稳定。故在这使用ver1.2版本。

**1.下载hadoop.1.2.1.tar.gz**

**2.安装Hadoop**

　　使用命令在相应的目录下：
　　

> tar -zxvf hadoop.1.2.1.tar.gz

### **2.4 配置Hadoop**

**1.配置四个文件**

**A.core-site.xml**

```
<property>
<name>hadoop.tmp.dir</name>
<value>/hadoop</value>
<discription>Hadoop的工作目录</discription>
</property>
<property>
<name>dfs.tmp.dir</name>
<value>/hadoop/name</value>
<discription>HDFS的namenode工作目录</discription>
</property>
<property>
<name>fs.default.name</name>
<value>hdfs://localhost:9000</value>
<discription>namenode的访问方式</discription>
</property>
```

**B.hdfs-site.xml**

```
<property>
<name>dfs.data.dir</name>
<value>/hadoop/data</value>
<discription>datanode的存放目录</discription>
</property>
```

**C.mapred-site.xml**

```
<property>
<name>mapred.job.tracker</name>
<value>localhost:9001</value>
<discription>MapReduce的地址与端口</discription>
</property>
```

**D.hadoop-env.xml**

　　配置$JAVA_HOME

```
export $JAVA_HOME=/home/gaoziqiang/devel/jdk/jdk_8/jdk1.8.0
```

**2.声明$HADOOP_HOME环境变量**

　　在/etc/profile文件。
　　

```
export HADOOP_HOME=/home/gaoziqiang/devel/hadoop/hadoop-1.2.1
```

## **3 启动Hadoop**
**1初始化文件系统**

　　在HADOOP_HOME/bin：
　　

> ./hadoop namenode -format

** 2.启动Hadoop**

　　在HADOOP_HOME/bin：
　　

> ./start-all.sh

** 3.检测是否启动成功**

　　使用命令:

> jps

　　成功后会显示一下五项内容，缺一不可。
　　![jps](http://img.blog.csdn.net/20170808160747908?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzM0Mjk5Njg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

