package utils;


import enums.UserAgentEnum;

public class UserAgentUtils {





    private static final String WX = "micromessenger";

    private static final String QQ = "qq";


    private static final String ALI = "alipayclient";


    private static final String ALIAPP = "dingtalk";

    private int code;


    public boolean isWx;

    public boolean isAlipay;

    public boolean isDing;


    public int getCode() {
        return code;
    }

    public UserAgentUtils(String userAgent, int type) {

        if (null == userAgent){
            return ;
        }
        switch (type) {
            case 1:
                build(userAgent);
                break;
            case 2:
                innerBuild(userAgent);
                break;
            default:
                break;
        }
    }

    public UserAgentUtils(String userAgent) {
        build(userAgent);
    }

    private void build(String userAgent) {
        String agent = userAgent.toLowerCase();
        init();
        if (agent.indexOf(WX) > 0 || agent.indexOf(QQ) > 0) {
            isWx = true;
            code= UserAgentEnum.WX.getCode();
            return;
        } else if (agent.indexOf(ALIAPP) > 0 || agent.indexOf(ALI) > 0) {
            isAlipay = true;
            code= UserAgentEnum.ALIPAY.getCode();
            return;
        }
    }

    private void innerBuild(String userAgent) {
        String agent = userAgent.toLowerCase();
        init();
        if (agent.indexOf(WX) > 0) {
            isWx = true;
            code= UserAgentEnum.WX.getCode();
            return;
        } else if (agent.indexOf(ALI) > 0) {
            isAlipay = true;
            code= UserAgentEnum.ALIPAY.getCode();
            return;
        } else if (agent.indexOf(ALIAPP) > 0) {
            isDing = true;
            code= UserAgentEnum.DING.getCode();
            return;
        }
    }

    private void init() {
        isWx = false;
        isAlipay = false;
        isDing = false;
    }

}
