package designpatterm.builder;

/**
 * @author baixiaobin
 */
public class User {

    private final int id;

    private final String name;

    private final String sex;

    private final String des;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getDes() {
        return des;
    }

    public static class Builder {

        private final int id;
        private final String name;

        private String sex;

        private String des;

        public User build() {
            return new User(this);
        }

        /**
         * @param id   主键id
         * @param name 名称
         */
        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder des(String des) {
            this.des = des;
            return this;
        }

    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.des = builder.des;
        this.sex = builder.sex;
    }
}
