package enums;

public enum UserAgentEnum {

    ALIPAY(1,"支付宝"),
    WX(1,"微信"),
    DING(2,"订钉钉");

    private int code;
    private String desc;

    public boolean isAlipay() {
        return this == ALIPAY;
    }

    public boolean isWx() {
        return this == WX;
    }

    public boolean isDing() {
        return this == DING;
    }

    public static UserAgentEnum create(Integer code) {
        if(code == null) {
            return ALIPAY;
        }

        for(UserAgentEnum type : values()) {
            if(code.equals(type.code)) {
                return type;
            }
        }

        return ALIPAY;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    private UserAgentEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
