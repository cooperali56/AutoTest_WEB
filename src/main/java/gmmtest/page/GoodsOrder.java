package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import gmmtest.tools.FunUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 购买下单页面和支付
 */
public class GoodsOrder extends BasePage {

    private String byData_goodsOrder;

    public GoodsOrder() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_goodsOrder = ExcelUtils.getExcelCell("pctestcase.xlsx", "buyandpay", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(GoodsOrder.class);

    /**
     * 购买支付
     *
     * @param insurance 保障选择
     * @return
     */
    public String buyAndPay(String insurance) {
        log4j.info("···开始" + elementText(returnExcelElement(byData_goodsOrder, "购买类型")));
        //针对账号进行保障选择
        if (elementText(returnExcelElement(byData_goodsOrder, "购买类型")).contains("账号")) {
            //已保商品无操作
            if ("保障".equals(insurance)) {
                //无保障商品购买三十天保障
            } else if ("无保障".equals(insurance)) {
                elementClick(returnExcelElement(byData_goodsOrder, "30天保障"));
                //无保障商品部购买保障
            } else if ("双无商品".equals(insurance)) {
                elementClick(returnExcelElement(byData_goodsOrder, "无保障"));
            }
        }

        //角色名称和等级
        List<WebElement> inputs = getElements(returnExcelBy(byData_goodsOrder, "全输入"));
        if (inputs != null) {
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).getAttribute("placeholder").contains("角色名称")) {
                    inputs.get(i).sendKeys("芜湖起飞");
                }
                if (inputs.get(i).getAttribute("placeholder").contains("角色等级")) {
                    inputs.get(i).clear();
                    inputs.get(i).sendKeys("99");
                }
            }
        }
        log4j.info("···" + elementText(returnExcelElement(byData_goodsOrder, "商品总额")) + elementText(returnExcelElement(byData_goodsOrder, "支付总额")));

        //确定支付
        elementClick(returnExcelElement(byData_goodsOrder, "确定"));

        //确定支付提示
        WebElement payPrompt = getElement(returnExcelBy(byData_goodsOrder, "确定支付提示"));
        if (isElementEffective(payPrompt)) {
            throw new ElementNotVisibleException("!确定支付有提示报错:" + elementText(payPrompt));
        }

        awaitEnforce(3000);

        /*
            支付~~~
         */

        //如果钱包未登录
        WebElement qb = getElement(returnExcelBy(byData_goodsOrder, "ifPB登陆"));
        if (qb != null) {
            //钱包登陆
            goFrame(returnExcelElement(byData_goodsOrder, "qbFrame"));
//            Login login = new Login();
//            login.smsLoginStep()
        }

        goFrame(returnExcelElement(byData_goodsOrder, "qbFrame"));
        log4j.info("···" + elementText(returnExcelElement(byData_goodsOrder, "支付信息")));
        outFrame();

        //解码获取orderId
        String url = null;
        try {
            String url1 = java.net.URLDecoder.decode(getUrl(), StandardCharsets.UTF_8.name());
            url = java.net.URLDecoder.decode(url1, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return FunUtils.regularValue(url, "(?<=(order_id=)).*?(?=(&))");
    }
}