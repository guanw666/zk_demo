package com.me;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkApiTest {
    @Test
    public void test() throws IOException, InterruptedException, KeeperException {
        // 1.创建zookeeper连接
        ZooKeeper zooKeeper = new ZooKeeper("39.108.162.100:2181", 10000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("触发了" + watchedEvent.getType() + "的事件");
            }
        });

        Thread.sleep(10000);
        // 2.创建父节点
//        zooKeeper.create("/node", "nodeValue".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 3.创建子节点
//        zooKeeper.create("/node/childNode", "childNodeValue".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 4.获取节点中的值
        final byte[] data = zooKeeper.getData("/node/childNode", false, null);
        System.out.println("get /node/childNode:" + new String(data));
        // 5.获取children
        final List<String> childrens = zooKeeper.getChildren("/", false);
        for (String children : childrens) {
            System.out.println(new String(children.getBytes()));
        }
        // 6.修改节点的值(-1可以匹配到任意版本的值)
        zooKeeper.setData("/node", "nodeNewValue".getBytes(), -1);
        final byte[] data1 = zooKeeper.getData("/node", false, null);
        System.out.println("get /node:" + new String(data1));
        // 7.判断某个节点是否存在
        final Stat s = zooKeeper.exists("/aaa", false);
        System.out.println(s);
        // 8.删除某个节点
        zooKeeper.del871`ete("/node/childNode",-1);
    }
}
