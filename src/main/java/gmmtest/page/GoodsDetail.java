package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * 商品详情
 */
public class GoodsDetail extends BasePage {

    private String byData_goods;

    public GoodsDetail() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_goods = ExcelUtils.getExcelCell("pctestcase.xlsx", "goods", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(GoodsDetail.class);

    /**
     * 详情页操作
     *
     * @param select 立即购买/编辑/重新上架/擦亮/下架
     */
    public void goodsOperate(String select) {
        //加载操作元素
        By operate = byFilter("xpath", "//*[text()='" + select + "']");
        awaitDisplay(operate, 5);
        //操作
        elementClick(getElement(operate));
        sms();
        //下架提示
        if ("下架".equals(select)) {
            elementClick(getElementsEffective(getElements(returnExcelBy(byData_goods,"下架确定"))));
            awaitEnforce(1000);
            if (getElement(returnExcelBy(byData_goods, "下架确定")) == null) {
                log4j.info("下架成功");
            }
            awaitEnforce(1000);
        }
    }
}