package testclass;

import gmmtest.modules.BuyGoods;
import gmmtest.tools.ExcelUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class BuyFlow {

    BuyGoods buyGoods = new BuyGoods();

    @Test
    @Parameters({"browser"})
    public void initialize(String browser) {
        buyGoods.initialize(browser);
    }

    /*
        买家登陆
     */
    @Test(dataProvider = "buyerLogin")
    public void buyer_login(String account, String code, String expect) {
        buyGoods.buyer_login(account, code);
    }

    @DataProvider(name = "buyerLogin")
    public Object[][] buyLoginData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "login", 4, 4, 5, 7);
    }

    /*
        购买到取消订单流程
     */
    @Test(dataProvider = "buyData")
    public void buy_flow(String goodsName, String goodsType, String indemnity) {
        buyGoods.selectGoods(goodsName, goodsType, indemnity);
        String order_id = buyGoods.goodsOrder(indemnity);
        buyGoods.orderCancel(order_id);
    }

    @DataProvider(name = "buyData")
    public Object[][] buyTestData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "buyandpay", 2, 6, 5, 7);
    }

    /**
     * 关闭浏览器
     */
    @AfterSuite
    public void out() {
        buyGoods.outBrowser();
    }
}