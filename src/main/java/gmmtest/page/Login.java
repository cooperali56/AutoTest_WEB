package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import static gmmtest.tools.UrlConfig.*;

/**
 * 登录
 */
public class Login extends BasePage {

    //登录的定位数据
    private String byData_login;

    public Login() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_login = ExcelUtils.getExcelCell("pctestcase.xlsx", "login", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(Login.class);

    /**
     * 登录前步骤
     */
    public void loginBeforeStep() {
        //打开官网主页
        openUrl(GMM_URL);
        awaitEnforce(3000);
        //登录按钮的定位值可能会有变化
        nullClick(returnExcelElement(byData_login, "登录1"));
        nullClick(returnExcelElement(byData_login, "登录2"));
        nullClick(returnExcelElement(byData_login, "登录3"));
        //进去frame
        goFrame(returnExcelElement(byData_login, "登录frame"));
    }

    /**
     * 短信验证方式登录步骤
     *
     * @param phoneCode
     * @param
     */
    public String smsLoginStep(String phoneCode, String sms) {
        log4j.info("···手机短信方式登录中");
        elementInput(returnExcelElement(byData_login, "手机号输入"), phoneCode);
        elementClick(returnExcelElement(byData_login, "获取验证码"));
        try {
            awaitDisplay(returnExcelBy(byData_login, "滑动"), 5);
            dragElement(returnExcelElement(byData_login, "滑动"), 200, 0);
        } catch (Exception e) {
        }
        elementInput(returnExcelElement(byData_login, "短信验证码"), sms);
        elementClick(returnExcelElement(byData_login, "登录按钮"));
        awaitEnforce(2000);
        String actual = elementText(returnExcelElement(byData_login, "提示"));
        String accountName = elementText(returnExcelElement(byData_login, "用户名"));
        log4j.info("···"+accountName + ",登录成功啦强啊宝贝!");
        return actual;
    }

    /**
     * 账号密码方式登录步骤
     *
     * @param account
     * @param pwd
     * @return
     */
    public String accountLoginStep(String account, String pwd) {
        log4j.info("账号密码方式登录中···");
        //点击国旗选择香港
        elementClick(returnExcelElement(byData_login, "国旗"));
        elementClick(returnExcelElement(byData_login, "区名香港"));
        elementInput(returnExcelElement(byData_login, "账号输入"), account);
        elementInput(returnExcelElement(byData_login, "密码输入"), pwd);
        elementClick(returnExcelElement(byData_login, "密码登录"));
        dragElement(returnExcelElement(byData_login, "滑动"), 200, 0);
        awaitEnforce(1000);
        String actual = elementText(returnExcelElement(byData_login, "提示"));
        String accountName = elementText(returnExcelElement(byData_login, "用户名"));
        log4j.info(accountName + ",登录成功啦强啊宝贝!");
        return actual;
    }

    /**
     * 注销当前账号
     */
    public void logout() {
        openUrl(GMM_URL);
        WebElement logout = getElement(returnExcelBy(byData_login, "注销"));
        if (isElementEffective(logout)) {
            elementClick(logout);
        } else {
            clearCookies();
        }
        log4j.info("宝贝再见!");
    }
}