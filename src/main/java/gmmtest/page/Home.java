package gmmtest.page;

import gmmtest.tools.ExcelUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static gmmtest.tools.UrlConfig.*;

/**
 * 官网首页
 */
public class Home extends BasePage {

    //首页的定位数据
    private String byData_home;

    public Home() {
        getExcelByData();
    }

    @Override
    protected void getExcelByData() {
        byData_home = ExcelUtils.getExcelCell("pctestcase.xlsx", "home", 2, 4);
    }

    private final Logger log4j = Logger.getLogger(Home.class);

    /**
     * 首页站点导航
     *
     * @param select
     */
    public void siteMap(String select) {
        elementClick(returnExcelElement(byData_home, "站点地图"));
        elementClick(getElement(By.xpath("//a[text()='" + select + "']")));
        awaitEnforce(2000);
    }

    /**
     * 出售前准备
     *
     * @param gameName 游戏名
     * @param gameType 商品类型
     * @param permit   通行证
     */
    public void goSellStep(String gameName, String gameType, String permit) {
        //打开选择商品地址
        openUrl(GOSELL_URL);
        log4j.info("···准备出售[" + gameName + "]游戏的[" + gameType + "]商品");
        elementClick(getElement(By.xpath("//*[@id='gameList']//a[contains(text(),'" + gameName + "')]")));
        awaitEnforce(1000);
        elementClick(getElement(By.linkText(gameType)));
        awaitEnforce(1000);
        //可需要的短信验证
        if (isElementEffective(returnExcelElement(byData_home, "短信账号列表"))) {
            elementClick(returnExcelElement(byData_home, "短信账号列表"));
            sms();
        }
        //获取下一步按钮元素用于判断所选通行证是否能上架
        WebElement nextStep = returnExcelElement(byData_home, "下一步");
        boolean next = nextStep.isEnabled();
        if (next) {
            elementClick(nextStep);
        }
        //随机选择可用通行证
        if (permit.equals("")) {
            while (!next) {
                List<WebElement> list = getElements(returnExcelBy(byData_home, "通行证列表"));
                for (int i = 0; i < list.size(); i++) {
                    elementClick(list.get(i));
                    awaitEnforce(2000);
                    if (nextStep.isEnabled()) {
                        elementClick(nextStep);
                        next = nextStep.isEnabled();
                        break;
                    }
                    //触发通行证警告
                    WebElement prompt = returnExcelElement(byData_home, "通行证提示");
                    if (prompt != null && prompt.isDisplayed()) {
                        log4j.info("该通行证[" + list.get(i).getText() + "]出现提示：" + elementText(prompt));
                        elementClick(getElementsEffective(getElements(returnExcelBy(byData_home, "确定"))));
                    }
                    //校验是否需要角色信息
                    if (isElementEffective(getElement(returnExcelBy(byData_home, "角色列表")))) {
                        List<WebElement> roleList = getElements(returnExcelBy(byData_home, "角色列表"));
                        for (int j = 0; j < roleList.size(); j++) {
                            elementClick(roleList.get(i));
                            log4j.info("上架角色信息=" + elementText(roleList.get(i)));
                            if (isElementEffective(nextStep)) {
                                elementClick(nextStep);
                                awaitEnforce(1000);
                                next = nextStep.isEnabled();
                                break;
                            }
                        }
                        if (isElementEffective(nextStep)) {
                            next = nextStep.isEnabled();
                            break;
                        }
                    }
                }
                if (!isElementEffective(nextStep)) {
                    throw new NullPointerException("此账号的通行证在’" + gameName + "‘游戏内均不可用");
                }
            }
        } else {
            //指定通行证操作
            elementClick(getElement(By.xpath("//*[text()='" + permit + "']")));
            //触发通行证警告
            WebElement prompt = returnExcelElement(byData_home, "通行证提示");
            if (prompt != null && prompt.isDisplayed()) {
                elementClick(getElementsEffective(getElements(returnExcelBy(byData_home, "确定"))));
                throw new NullPointerException("您所选择的通行证'" + permit + "'有警告提示;" + prompt.getText());
            }
            //点击下一步
            elementClick(nextStep);
        }
        //继续上架
        try {
            elementClick(getElementsEffective(getElements(returnExcelBy(byData_home, "继续上架"))));
        } catch (Exception e) {
        }
    }

    /**
     * 首页顶部搜索进商品列表通过book_id进商品详情
     *
     * @param gameName 游戏名称
     * @param gameType 游戏类型
     * @param bookId   商品id或者购买商品所需属性
     */
    public void goGoods(String gameName, String gameType, String bookId) {
        openUrl(GMM_URL);
        log4j.info("···准备搜索[" + gameName + "]游戏的[" + gameType + "]商品列表信息");
        elementClick(returnExcelElement(byData_home, "游戏名称"));
        elementInput(returnExcelElement(byData_home, "游戏名输入"), gameName);
        elementClick(getElement(By.linkText(gameName)));
        elementClick(getElement(By.linkText(gameType)));
        //搜索
        elementClick(returnExcelElement(byData_home, "搜索"));
        awaitEnforce(2000);
        Random random = new Random();
        List<WebElement> goodsList;
        switch (bookId) {
            case "随意":
            case "":
                goodsList = getElements(returnExcelBy(byData_home, "商品列表"));
                elementClick(goodsList.get(random.nextInt(goodsList.size())));
                break;
            case "保障":
            case "无保障":
                if ("保障".equals(bookId)) {
                    elementClick(returnExcelElement(byData_home, "保障"));
                } else {
                    elementClick(returnExcelElement(byData_home, "无保障"));
                }
                goodsList = getElements(returnExcelBy(byData_home, "筛选列表"));
                elementClick(goodsList.get(random.nextInt(goodsList.size())));
                break;
            case "双无商品":
                elementClick(returnExcelElement(byData_home, "无保障"));
                goodsList = getElements(returnExcelBy(byData_home, "筛选列表"));
                elementClick(goodsList.get(random.nextInt(goodsList.size())));
                break;
            default:
                elementClick(getElement(By.xpath("//li[@data-bookid='" + bookId + "']/a")));
                break;
        }
        awaitEnforce(2000);
        cutNewWindowToClose(true);
        awaitEnforce(2000);
    }
}