package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * 订单详情页
 */
public class OrderDetail extends BasePage {

    private String byData_order;

    public OrderDetail() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_order = ExcelUtils.getExcelCell("pctestcase.xlsx", "order", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(OrderDetail.class);

    /**
     * 订单操作
     *
     * @param select
     */
    public void orderOperation(String select) {
        elementClick(getElement(By.xpath("//*[text()='" + select + "']")));
        if ("取消订单".equals(select)){
            awaitEnforce(2000);
            elementClick(returnExcelElement(byData_order,"选择原因"));
            awaitEnforce(1000);
            elementClick(returnExcelElement(byData_order,"取消原因"));
            awaitEnforce(1000);
            elementClick(returnExcelElement(byData_order,"确定原因"));
            awaitEnforce(1000);
            //操作之后的状态
            String state = elementText(returnExcelElement(byData_order,"取消状态"));
            if (state.contains("关闭")){
                log4j.info("取消订单成功关闭="+state);
            }
        }
    }
}
