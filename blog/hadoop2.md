# Hadoop系列－－Incompatible namespaceIDs问题的解决
## **1 问题描述**
　　使用jps命令查看Hadoop进程启动情况，发现datanode总是启动不起来。查看log，如下：


```
2017-08-11 14:46:04,213 ERROR org.apache.hadoop.hdfs.server.datanode.DataNode: java.io.IOException: Incompatible namespaceIDs in /hadoop/data: namenode namespaceID = 179997037; datanode namespaceID = 331494645
```

## **2 运行环境**
　　**ubuntu16.04 + hadoop1.2.1 + JDK1.8**
## **3 问题原因**
　　log中已经给出了问题的原因：
　　

```
Incompatible namespaceIDs in /hadoop/data: namenode namespaceID = 179997037; datanode namespaceID = 331494645
```
　　两个namespacesID出现冲突。
## **4 问题解决**
　　查阅了好多资料，没有找到一个永久的解决方法。

    下面的两个解决方法可以作为参考，其中第二个是我采用的方法，缺点就是每次format之前都要手动删除{dfs.data.dir}/data/目录，很麻烦。

　　首先查看core-site.xml和hdfs-site.xml文件中{dfs.data.dir}和{dfs.name.dir}的位置。
### **4.1 失败的解决方法**
　　按照blog的提示，修改{dfs.data.dir}/current/VERSION和{dfs.name.dir}/VERSION中`namespaceID`，使两者一致。

　　具体解决步骤如下：

**1.stop the datanode**

　　命令
> bin/stop-all.sh

**2.修改{dfs.data.dir}/current/VERSION和{dfs.name.dir}/VERSION中`namespaceID`**

　　文件大体内容如下：
```
#Fri Aug 11 15:45:09 CST 2017
namespaceID=1420739432
storageID=DS-1137050874-127.0.1.1-50010-1502437509662
cTime=0
storageType=DATA_NODE
layoutVersion=-41
```

**3.restart the datanode**
### **4.2 成功的解决方法**
**1.stop the datanode**

　　命令
> bin/stop-all.sh

**2.手动删除{dfs.data.dir}/data/目录**

　　首先查看core-site.xml和hdfs-site.xml文件中{dfs.data.dir}和{dfs.name.dir}的位置；然后手动删除{dfs.data.dir}/data/目录。

**3.restart the datanode**
## **5 优化解决**
　　能否找到一个更好的方法，不用每次都要手动删除{dfs.data.dir}/data/目录，太麻烦。

