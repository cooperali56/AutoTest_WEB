package gmmtest.modules;

import gmmtest.page.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * 主流程
 */
public class GmmMain {

    private WebDriver driver;

    private Home home;
    private Login login;
    private GoodsFill goodsFill;
    private GoodsDetail goodsDetail;
    private GoodsOrder goodsOrder;
    private GmmMy gmmMy;
    private OrderDetail orderDetail;

    private final Logger log4j = Logger.getLogger(GmmMain.class);

    /**
     * 初始化
     *
     * @param browser 浏览器选择
     */
    public void initialize(String browser) {
        home = new Home();
        home.browserSelect(browser);
        home.awaitHide();
        home.windowMax();
        driver = home.getDriver();
    }

    /**
     * 用户登陆
     *
     * @param account 账号
     * @param code    短息码
     * @param e       预期
     */
    public void user_login(String account, String code, String e) {
        login = new Login();
        login.setDriver(driver);

        login.loginBeforeStep();
        login.smsLoginStep(account, code);
    }

    /**
     * 上架流程
     *
     * @param goodsName 商品游戏名
     * @param goodsType 商品类型
     */
    public void sell_flow(String goodsName, String goodsType) {
        goodsFill = new GoodsFill();
        goodsFill.setDriver(driver);
        goodsDetail = new GoodsDetail();
        goodsDetail.setDriver(driver);
        //首次上架
        home.goSellStep(goodsName, goodsType, "");
        String bookId = goodsFill.inputGoodsDataStep();
        log4j.info("···book_id=" + bookId);
        goodsFill.issue();
        //编辑
        home.goGoods(goodsName, goodsType, bookId);
        goodsDetail.goodsOperate("编辑");
        goodsFill.edit();
        goodsFill.issue();
        home.goGoods(goodsName, goodsType, bookId);
        goodsDetail.goodsOperate("下架");
        //重新上架
        goodsDetail.goodsOperate("重新上架");
        goodsFill.anew();
        goodsFill.issue();
        home.goGoods(goodsName, goodsType, bookId);
        goodsDetail.goodsOperate("下架");
    }

    /**
     * 购买流程
     *
     * @param goodsName   商品游戏名
     * @param goodsType   商品类型
     * @param bookIdOrIns 商品id或者商品保障类型
     */
    public void buy_flow(String goodsName, String goodsType, String bookIdOrIns) {
        goodsOrder = new GoodsOrder();
        goodsOrder.setDriver(driver);
        gmmMy = new GmmMy();
        gmmMy.setDriver(driver);
        orderDetail = new OrderDetail();
        orderDetail.setDriver(driver);
        goodsDetail = new GoodsDetail();
        goodsDetail.setDriver(driver);
        //购买
        home.goGoods(goodsName, goodsType, bookIdOrIns);
        goodsDetail.goodsOperate("立即购买");
        String order_id = goodsOrder.buyAndPay(bookIdOrIns);
        log4j.info("···order_id=" + order_id);
        home.siteMap("我买到的");
        gmmMy.orderList_goOrder(order_id);
        orderDetail.orderOperation("取消订单");
    }

    /**
     * 关闭浏览器
     */
    public void outBrowser() {
        home.browserOff();
    }
}