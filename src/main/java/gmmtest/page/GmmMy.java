package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * gmm个人中心
 */
public class GmmMy extends BasePage{

    public String byData_my;

    public GmmMy() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_my =  ExcelUtils.getExcelCell("pctestcase.xlsx", "my", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(GmmMy.class);

    /**
     * 订单列表进指定订单详情
     * @param order_id
     */
    public void orderList_goOrder(String order_id){
        elementClick(getElement(By.xpath("//*[@data-orderid='" + order_id + "']/../../../div[2]//a[text()='订单详情']")));
        cutNewWindowToClose(true);
        awaitEnforce(2000);
    }
}