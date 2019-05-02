package utils;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    void  onlyMe(Integer my){
        synchronized (my){
            doSomeThing();
        }
    }

    synchronized  void doSomeThing(){
        ReentrantLock lock = new ReentrantLock();
        System.out.println("====");
    }
}
