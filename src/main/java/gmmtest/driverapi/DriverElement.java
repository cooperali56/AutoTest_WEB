package gmmtest.driverapi;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * 元素操作
 */
public class DriverElement extends DriverWindow {

    private final Logger log4j = Logger.getLogger(DriverElement.class);

    /**
     * 判断定位值by是否存在
     *
     * @param by 定位值
     * @return 真假
     */
    private boolean isByOk(By by) {
        try {
            super.getDriver().findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * By元素类型区分
     *
     * @param byMethod 定位方法
     * @param byValue  定位value
     * @return by
     */
    public By byFilter(String byMethod, String byValue) {
        By by = null;
        switch (byMethod) {
            case "id":
                by = By.id(byValue);
                break;
            case "xpath":
                by = By.xpath(byValue);
                break;
            case "name":
                by = By.name(byValue);
                break;
            case "className":
                by = By.className(byValue);
                break;
            case "tagName":
                by = By.tagName(byValue);
                break;
            case "linkText":
                by = By.linkText(byValue);
                break;
            case "partial":
                by = By.partialLinkText(byValue);
                break;
            case "css":
                by = By.cssSelector(byValue);
                break;
            default:
                log4j.debug("定位值类型" + byMethod + "有误!");
                return null;
        }
        return by;
    }

    /**
     * 返回有效元素
     *
     * @param by
     * @return
     */
    public WebElement getElement(By by) {
        if (isByOk(by)) {
            return super.getDriver().findElement(by);
        } else {
            return null;
        }
    }

    /**
     * 返回有效元素列表
     *
     * @param by
     * @return
     */
    public List<WebElement> getElements(By by) {
        if (isByOk(by)) {
            return super.getDriver().findElements(by);
        } else {
            return null;
        }
    }

    /**
     * 从元素集合中返回有效可操作的元素
     *
     * @param elements
     * @return
     */
    public WebElement getElementsEffective(List<WebElement> elements) {
        WebElement element = null;
        for (int i = 0; i < elements.size(); i++) {
            if (isElementEffective(elements.get(i))) {
                element = elements.get(i);
            }
        }
        return element;
    }

    /**
     * 返回可见可操作元素
     *
     * @param element
     * @return
     */
    public boolean isElementEffective(WebElement element) {
        if (element != null && element.isDisplayed() && element.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 有效点击
     *
     * @param element
     */
    public void elementClick(WebElement element) {
        if (element == null) {
            log4j.debug("老铁空的定位元素点击不了啊!");
        }
        element.click();
        awaitEnforce(500);
    }

    /**
     * 无效点击
     *
     * @param element
     */
    public void nullClick(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
        }
    }

    /**
     * 输入
     *
     * @param element
     * @param value
     */
    public void elementInput(WebElement element, String value) {
        if (element == null) {
            log4j.debug("老铁空的定位元素输入不了啊!");
        }
        element.clear();
        awaitEnforce(500);
        element.sendKeys(value);
    }

    /**
     * 返回元素文本
     *
     * @param element
     * @return
     */
    public String elementText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 元素拖动
     *
     * @param element 被操作元素
     * @param x       x轴移动多少像素
     * @param y       Y轴移动像素值
     */
    public void dragElement(WebElement element, int x, int y) {
        Actions actions = new Actions(super.getDriver());
        actions.dragAndDropBy(element, x, y).perform();
        actions.release();
    }
}