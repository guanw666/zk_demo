package com.me.zkstudy;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式配置中心demo
 */
public class ZookeeperCfgSync implements Watcher {

    /**
     * 连接信号
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public void process(WatchedEvent watchedEvent) {
        // zk连接成功通知事件
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            if (Event.EventType.None == watchedEvent.getType() && watchedEvent.getPath() == null) {
                connectedSemaphore.countDown();
            }
            // 节点数据变化通知事件
            else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("配置已经修改，新的值为：" + new String(zk.getData(watchedEvent.getPath(), true, stat)));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 节点位置
        String path = "/username";
        // 连接zookeeper并且注册一个默认的监听器（异步）
        zk = new ZooKeeper("39.108.162.100:2181", 5000, new ZookeeperCfgSync());
        // 等待zk连接成功通知
        connectedSemaphore.await();
        System.out.println(new String(zk.getData(path, true, stat)));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
