package designpatterm.wrapper;

public class ForeignCook implements Cook {
    @Override
    public void cookDinner() {
        System.out.println("外国厨师做晚饭");
    }
}
