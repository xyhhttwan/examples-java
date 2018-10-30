package zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class zk {

    public static void main(String[] args) {
        String connectString = "127.0.0.1:2181";
        int sessionTimeout = 4000;
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.getPath());
            }
        };


        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
            List<String> list = zooKeeper.getChildren("/configs", false);
            for (String path : list) {
                System.out.println(path);
                byte[] b = zooKeeper.getData("/configs/"+path, false, null) ;
                System.out.println( new String(b) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();


        }
    }
}
