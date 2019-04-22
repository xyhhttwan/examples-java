package utils;

public class Main {

    void  onlyMe(Integer my){
        synchronized (my){
            doSomeThing();
        }
    }

    synchronized  void doSomeThing(){
        System.out.println("====");
    }
}
