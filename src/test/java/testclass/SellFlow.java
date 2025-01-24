package testclass;

import gmmtest.modules.SellGoods;
import gmmtest.tools.ExcelUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class SellFlow {

    SellGoods sellGoods = new SellGoods();

    @Test
    @Parameters({"browser"})
    public void initialize(String browser){
        sellGoods.initialize(browser);
    }

    /*
        登录
     */
    @DataProvider(name = "smsTestData")
    private Object[][] smsTestData() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "login", 2, 2, 5, 7);
    }
    @Test(dataProvider = "smsTestData")
    public void login_flow(String phone, String code, String expect){
        sellGoods.seller_login(phone,code);
    }

    /*
        出售
     */
    @DataProvider(name = "sell")
    private Object[][] sell() {
        return ExcelUtils.getExcelObject("pctestcase.xlsx", "sell", 2, 6, 5, 6);
    }
    @Test(dataProvider = "sell")
    public void sell_flow(String gameName,String gameType){
        //发布
        String bookId = sellGoods.sellGoods(gameName, gameType);
        //编辑
        sellGoods.editGoods(gameName,gameType,bookId);
        //重新上架
        sellGoods.anewGoods(gameName,gameType,bookId);
    }


    /**
     * 关闭浏览器
     */
    @AfterSuite
    public void out() {
        sellGoods.outBrowser();
    }
}
