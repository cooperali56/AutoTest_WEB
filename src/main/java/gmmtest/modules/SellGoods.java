package gmmtest.modules;

import gmmtest.page.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * 出售流程模块
 */
public class SellGoods {

    private WebDriver driver;

    private Home home;
    private Login login;
    private GoodsFill goodsFill;
    private GoodsDetail goodsDetail;

    private final Logger log4j = Logger.getLogger(SellGoods.class);

    /**
     * 初始化
     *
     * @param browser 浏览器
     */
    public void initialize(String browser) {
        home = new Home();
        home.browserSelect(browser);
        home.awaitHide();
        home.windowMax();
        driver = home.getDriver();
    }

    /**
     * 卖家登录
     *
     * @param acc  账号
     * @param code 密码或者短信
     */
    public void seller_login(String acc, String code) {
        login = new Login();
        login.setDriver(driver);
        login.smsLoginStep(acc, code);
    }

    /**
     * 首次上架
     *
     * @param gameName 游戏名称
     * @param gameType 商品类型
     */
    public String sellGoods(String gameName, String gameType) {
        home.goSellStep(gameName, gameType, "");
        goodsFill = new GoodsFill();
        goodsFill.setDriver(driver);

        String bookId = goodsFill.inputGoodsDataStep();
        goodsFill.issue();
        log4j.info("···book_id=" + bookId);
        return bookId;
    }

    /**
     * 编辑商品
     *
     * @param gameName 游戏名称
     * @param gameType 商品类型
     * @param bookId   商品编号
     */
    public void editGoods(String gameName, String gameType, String bookId) {
        home.goGoods(gameName, gameType, bookId);
        goodsDetail = new GoodsDetail();
        goodsDetail.setDriver(driver);

        goodsDetail.goodsOperate("编辑");
        goodsFill.edit();
        goodsFill.issue();
        home.goGoods(gameName, gameType, bookId);
        goodsDetail.goodsOperate("下架");
    }

    /**
     * 重新上架商品
     *
     * @param gameName 游戏名称
     * @param gameType 商品类型
     * @param bookId   商品编号
     */
    public void anewGoods(String gameName, String gameType, String bookId) {
        goodsDetail.goodsOperate("重新上架");
        goodsFill.anew();
        goodsFill.issue();
        home.goGoods(gameName, gameType, bookId);
        goodsDetail.goodsOperate("下架");
    }

    /**
     * 关闭浏览器
     */
    public void outBrowser() {
        goodsFill.browserOff();
    }
}