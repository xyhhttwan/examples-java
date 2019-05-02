package designpatterm.wrapper;

public class WashHeaderCook extends FilterCook {


    public WashHeaderCook(Cook cook) {
        this.cook = cook;
    }

    @Override
    public void cookDinner() {
        System.out.println("做晚饭前，先洗头");
        cook.cookDinner();
    }
}
