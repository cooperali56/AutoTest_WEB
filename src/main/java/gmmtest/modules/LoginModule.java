package gmmtest.modules;

import gmmtest.page.Login;
import org.testng.Assert;

/**
 * 该模块所触发的场景
 */
public class LoginModule {

    //出售流程构造需要页面的实例
    Login login = new Login();

    /**
     * 初始化
     * @param browser
     */
    public void initialize(String browser){
        login.browserSelect(browser);
        login.awaitHide();
        login.windowMax();
    }

    /**
     * 短信登录模块
     * @param phone
     * @param sms
     * @param e
     */
    public void smsLogin(String phone, String sms, String e) {
        login.loginBeforeStep();
        String s = login.smsLoginStep(phone, sms);
        Assert.assertEquals(s, e);
    }

    /**
     * 账号登录
     *
     * @param account
     * @param pwd
     * @param e
     */
    public void accLogin(String account, String pwd, String e) {
        login.loginBeforeStep();
        String s = login.accountLoginStep(account, pwd);
        Assert.assertEquals(s, e);
    }

    /**
     * 注销
     */
    public void logout() {
        login.logout();
    }

    /**
     * 关闭浏览器
     */
    public void outBrowser() {
        login.browserOff();
    }
}