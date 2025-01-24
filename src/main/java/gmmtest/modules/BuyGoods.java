package gmmtest.modules;

import gmmtest.page.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * 购买流程模块
 */
public class BuyGoods {

    private WebDriver driver;

    private Home home;
    private Login login;
    private GoodsDetail goodsDetail;
    private GoodsOrder goodsOrder;
    private GmmMy gmmMy;
    private OrderDetail orderDetail;

    private final Logger log4j = Logger.getLogger(BuyGoods.class);

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
     * 买家登陆
     */
    public void buyer_login(String account, String code) {
        login = new Login();
        login.setDriver(driver);
        login.smsLoginStep(account,code);
    }


    /**
     * 选择商品
     *
     * @param goodsName 商品游戏名
     * @param goodsType 商品类型
     */
    public void selectGoods(String goodsName, String goodsType, String bookIdOrIns) {
        home.goGoods(goodsName, goodsType, bookIdOrIns);
    }

    /**
     * 下单商品
     *
     * @param bookIdOrIns 商品编号或者投保选项
     * @return
     */
    public String goodsOrder(String bookIdOrIns) {
        goodsDetail = new GoodsDetail();
        goodsDetail.setDriver(driver);
        goodsOrder = new GoodsOrder();
        goodsOrder.setDriver(driver);

        goodsDetail.goodsOperate("立即购买");
        String order_id = goodsOrder.buyAndPay(bookIdOrIns);
        log4j.info("···order_id="+order_id);
        return order_id;
    }

    /**
     * 取消订单
     *
     * @param order_id 订单id
     */
    public void orderCancel(String order_id) {
        home.siteMap("我买到的");
        gmmMy = new GmmMy();
        gmmMy.setDriver(driver);
        orderDetail = new OrderDetail();
        orderDetail.setDriver(driver);

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