package zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class Watch {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        reg();
    }


    static void reg() throws IOException, KeeperException, InterruptedException {

        Watcher wh = new Watcher() {

            @Override
            public void process(WatchedEvent event) {

                System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型：" + event.getType());

            }

        };

        ZooKeeper zk = new ZooKeeper("dubbo.zk.url:2181", 500000, wh);


        Stat stat = zk.exists("/root", true);
        System.out.println(stat);

        zk.create("/root/school", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

        zk.delete("/root/school", -1);

        zk.close();
    }




}
