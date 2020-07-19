package com.me;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

public class ZkApiTest {
    @Test
    public void test() throws IOException {
        // 1.创建zookeeper连接
        ZooKeeper zooKeeper = new ZooKeeper("39.108.162.100:2181", 10000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("触发了" + watchedEvent.getType() + "的事件");
            }
        });
        System.out.println("aaa");
    }
}
