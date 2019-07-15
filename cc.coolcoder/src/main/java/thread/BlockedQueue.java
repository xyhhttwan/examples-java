package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue<T> {

    final Lock lock = new ReentrantLock();

    /**
     * 条件变量，队列不满
     */
    final Condition notfull = lock.newCondition();

    /**
     * 条件变量 队列满了
     */
    final Condition notempty = lock.newCondition();

    List<T> list = new ArrayList<>();

    final int max = 2;

    void enq(T t) {
        System.out.println("开始生产:" + t);
        try {
            lock.lock();
            while (list.size() == max) {
                try {
                    System.out.println("满了暂停生产");
                    notfull.await();
                } catch (InterruptedException e) {

                }
            }
            list.add(t);
            notempty.signal();
        } finally {
            lock.unlock();
        }
        System.out.println("生产完成");

    }

    void eq(T t) {

        System.out.println("开始消费:" + t);
        try {
            lock.lock();
            while (list.size() <=0) {
                System.out.println("消费完了,等待生产");

                try {
                    notempty.await();
                } catch (InterruptedException e) {

                }
            }
            list.remove(t);
            notfull.signal();
        } finally {
            lock.unlock();
        }

        System.out.println("消费完毕");

    }

}
