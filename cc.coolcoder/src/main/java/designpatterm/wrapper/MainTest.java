package designpatterm.wrapper;

public class MainTest {


    public static void main(String[] args) {

        WashHandCook washHandCook = new WashHandCook( new ChineseCook());
        washHandCook.cookDinner();
        WashHeaderCook washHeaderCook = new WashHeaderCook(new ForeignCook());
        washHeaderCook.cookDinner();
    }
}
