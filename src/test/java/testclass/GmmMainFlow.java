package testclass;

import gmmtest.modules.GmmMain;
import gmmtest.tools.ExcelUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * G买卖主流程
 */
public class GmmMainFlow {

    GmmMain gmmMain = new GmmMain();

    /**
     * 初始化
     */
    @Test
    @Parameters({"browser"})
    public void initialize(String browser) {
       gmmMain.initialize(browser);
    }

    /**
     * 用户登陆
     *
     * @param acc    账号
     * @param code   登陆码
     * @param expect 验证
     */
    @Test//(dataProvider = "user")
    @Parameters({"account","code","expect"})
    public void user_login(String acc, String code, String expect) {
        gmmMain.user_login(acc, code, expect);
    }

    @DataProvider(name = "user")
    public Object[][] userData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "login", 4, 4, 5, 7);
    }

    /**
     * 用户出售流程
     *
     * @param goodsName 商品游戏
     * @param goodsType 商品类型
     */
    @Test(dataProvider = "sell")
    public void sell_flow(String goodsName, String goodsType) {
        gmmMain.sell_flow(goodsName, goodsType);
    }

    @DataProvider(name = "sell")
    public Object[][] sellData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "sell", 2, 6, 5, 6);
    }

    /**
     * 用户购买流程
     *
     * @param goodsName 商品游戏
     * @param goodsType 商品类型
     * @param indemnity 商品保障选择
     */
    @Test(dataProvider = "buy")
    public void buy_flow(String goodsName, String goodsType, String indemnity) {
        gmmMain.buy_flow(goodsName, goodsType, indemnity);
    }

    @DataProvider(name = "buy")
    public Object[][] buyData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "buyandpay", 2, 6, 5, 7);
    }

    /**
     * 关闭浏览器
     */
    @AfterSuite
    public void out() {
        gmmMain.outBrowser();
    }
}
