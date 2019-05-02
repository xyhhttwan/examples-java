package designpatterm.wrapper;

public class WashHandCook extends FilterCook {


    public WashHandCook(Cook cook) {
        this.cook = cook;
    }

    @Override
    public void cookDinner() {
        System.out.println("做晚饭前，先洗手");
        cook.cookDinner();
    }
}
