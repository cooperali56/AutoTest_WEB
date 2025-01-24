package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import gmmtest.tools.FunUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 填写发布商品信息
 */
public class GoodsFill extends BasePage {

    private String byData_goods;

    public GoodsFill() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_goods = ExcelUtils.getExcelCell("pctestcase.xlsx", "sell", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(GoodsFill.class);

    /**
     * 上架填写资料，数据固定，后续的话看情况在增加吧
     *
     * @return
     */
    public String inputGoodsDataStep() {
        awaitEnforce(3000);
        //该页面所有点击操作
        List<WebElement> allTagA = getElements(returnExcelBy(byData_goods, "tagA"));
        for (int i = 0; i < allTagA.size(); i++) {
            //筛选显示且可操作元素
            if (allTagA.get(i).isDisplayed()) {
                //筛选包含‘选择’文本的a元素
                if (allTagA.get(i).getText().contains("选择")) {
                    //点击包含‘选择’的a元素
                    awaitEnforce(500);
                    elementClick(allTagA.get(i));
                    awaitEnforce(500);
                    //获取当前a标签元素id属性值
                    String getId = allTagA.get(i).getAttribute("id");
                    //查看属性下是否有复选框：为什么不用selenium复选框的方法你可以去问问前端[doge]
                    List<WebElement> checkboxs = getElements(By.xpath("//*[@id='" + getId + "']/../dl[1]/dd"));
                    Random random = new Random();
                    //随意点击复选框
                    elementClick(checkboxs.get(random.nextInt(checkboxs.size())));
                    //再判断是否有三级复选框
                    WebElement checkboxPro = getElement(By.xpath("//*[@id='" + getId + "']/../dl[2]/dd"));
                    if (checkboxPro != null && checkboxPro.isDisplayed()) {
                        List<WebElement> checkboxPros = getElements(By.xpath("//*[@id='" + getId + "']/../dl[2]/dd"));
                        elementClick(checkboxPros.get(random.nextInt(checkboxPros.size())));
                        awaitEnforce(500);
                    }
                }
            }
        }

        //该页面所有input输入操作
        List<WebElement> allInputTag = getElements(returnExcelBy(byData_goods, "tagInput"));
        for (int i = 0; i < allInputTag.size(); i++) {
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("标题")) {
                allInputTag.get(i).sendKeys("这个平台真好大家好");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("最低")) {
                allInputTag.get(i).sendKeys("55");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("物品锁")) {
                allInputTag.get(i).sendKeys("2355");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("二级")) {
                allInputTag.get(i).sendKeys("7445");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("数量")) {
                allInputTag.get(i).sendKeys("550");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("交易号")) {
                allInputTag.get(i).sendKeys("202020");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("暗号")) {
                allInputTag.get(i).sendKeys("202020");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("密码")) {
                allInputTag.get(i).sendKeys("5123");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("装备名称")) {
                allInputTag.get(i).sendKeys("无敌大刀");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("件数")) {
                allInputTag.get(i).sendKeys("8");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("描述")) {
                allInputTag.get(i).sendKeys("阿吧阿吧阿吧阿吧");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("角色名称")) {
                allInputTag.get(i).sendKeys("奥利给");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("存放处")) {
                allInputTag.get(i).sendKeys("咯吱窝");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("商品库存")) {
                allInputTag.get(i).sendKeys("88");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("账号名称")) {
                if (allInputTag.get(i).isEnabled()){
                    allInputTag.get(i).sendKeys("gmmtest666");
                }
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("云盘链接")) {
                allInputTag.get(i).sendKeys("www.gmmnpnice.com");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("提取密码")) {
                allInputTag.get(i).sendKeys("0981");
            }
            if (allInputTag.get(i).isDisplayed() && allInputTag.get(i).getAttribute("placeholder").contains("造型名称")) {
                allInputTag.get(i).sendKeys("无敌很寂寞");
            }
        }

        //账号类型选择投保
        if (getElement(returnExcelBy(byData_goods, "投保")) != null) {
            elementClick(returnExcelElement(byData_goods, "投保"));
        }

        //图片上传
        if (getElement(returnExcelBy(byData_goods, "传图")) != null) {
            List<String> images = new ArrayList<>();
            File relative = new File("");
            Random random = new Random();
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\lzg.jpg");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\lzg1.jpg");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\chd.jpg");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\6464.png");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\man.jpeg");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\wuwu.jpg");
            images.add(relative.getAbsolutePath() + File.separator + "testImage\\gif666.gif");
            getElement(returnExcelBy(byData_goods, "传图")).sendKeys(images.get(random.nextInt(images.size())));
            awaitEnforce(2000);
        }
        return FunUtils.regularValue(getUrl(), "(?<=(book_id=)).*?(?=(&))");
    }

    /**
     * 发布商品
     */
    public void issue() {
        awaitEnforce(1000);
        elementClick(returnExcelElement(byData_goods, "发布"));
        awaitEnforce(500);
        log4j.info("···"+elementText(returnExcelElement(byData_goods, "发布信息")));
        elementClick(returnExcelElement(byData_goods, "发布确定"));
        sms();
        //确认发布提示
        WebElement prompt = getElement(returnExcelBy(byData_goods, "发布提示"));
        if (prompt.isDisplayed()) {
            elementClick(returnExcelElement(byData_goods, "确定"));
            throw new NullPointerException("···此通行证有警告提示：" + elementText(prompt));
        }
        if (elementText(returnExcelElement(byData_goods, "发布结果")).contains("成功")) {
            log4j.info("···发布结果="+elementText(returnExcelElement(byData_goods, "发布结果")));
        } else {
            log4j.debug("···商品上架失败");
        }
        elementClick(returnExcelElement(byData_goods, "返回首页"));
        try {
            windowPopUp(true);
        } catch (NoAlertPresentException e) {
        }
    }

    /**
     * 编辑标题
     */
    public void edit() {
        getElement(returnExcelBy(byData_goods, "编辑重新标题")).sendKeys("编辑");
    }

    /**
     * 重新标题
     */
    public void anew() {
        getElement(returnExcelBy(byData_goods, "编辑重新标题")).sendKeys("重新");
    }
}