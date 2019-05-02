package designpatterm.builder;

public class MainTest {

    public static void main(String[] args) {
        User user = new User.Builder(1, "zhangsan").des("hehe").sex("男").build();
        System.out.println(user.getSex());
    }
}
