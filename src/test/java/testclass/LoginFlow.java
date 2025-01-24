package testclass;

import gmmtest.modules.LoginModule;
import gmmtest.tools.ExcelUtils;
import org.testng.annotations.*;

/**
 * 登录流程的测试类
 */
public class LoginFlow {

    LoginModule loginModule = new LoginModule();

    @Test
    @Parameters({"browser"})
    public void openBrowser(String browser){
        loginModule.initialize(browser);
    }

    /*
        正常短信登录套
     */
    @DataProvider(name = "TestData")
    private Object[][] TestData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "login", 2, 2, 5, 7);
    }
    @Test(dataProvider = "TestData")
    public void noteLoginTest(String phone, String code, String expect){
        loginModule.smsLogin(phone, code, expect);
    }

    /*
       正常账号密码登录套
    */
    @DataProvider(name = "accTestData")
    private Object[][] accTestData() {
        Object[][] dataCases = ExcelUtils.getExcelObject("pctestcase.xlsx", "login", 5, 5, 5, 7);
        return dataCases;
    }
    @Test(dataProvider = "accTestData")
    public void accountLoginTest(String account, String code, String expect){
        loginModule.accLogin(account, code, expect);
    }

    @AfterClass
    public void logout(){
        loginModule.logout();
    }

    /**
     * 关闭浏览器
     */
    @AfterSuite
    public void out() {
        loginModule.outBrowser();
    }

}
