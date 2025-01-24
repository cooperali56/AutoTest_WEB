package gmmtest.driverapi;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 窗口操作
 */
public class DriverWindow extends BrowserDrivers {

    private final Logger log4j = Logger.getLogger(DriverWindow.class);

    /**
     * 打开url地址
     *
     * @param url 网址
     */
    public void openUrl(String url) {
        if (url != null) {
            super.getDriver().get(url);
            awaitEnforce(2000);
        } else {
            log4j.debug("url为空啊你搞毛线哦要我怎么打开!");
        }
    }

    /**
     * 获取当前窗口url
     *
     * @return 网址
     */
    public String getUrl() {
        return super.getDriver().getCurrentUrl();
    }

    /**
     * 刷新窗口
     */
    public void windowRefresh() {
        super.getDriver().navigate().refresh();
    }

    /**
     * 获取窗口标题
     *
     * @return 该窗口标题
     */
    public String getWindowTitle() {
        return super.getDriver().getTitle();
    }

    /**
     * 关闭标签页或窗口
     */
    public void windowClose() {
        super.getDriver().close();
    }

    /**
     * 切换新窗口
     *
     * @param close 是否关闭旧窗口
     */
    public void cutNewWindowToClose(boolean close) {
        Set<String> allHandles = super.getDriver().getWindowHandles();
        List<String> listHandles = new ArrayList<>(allHandles);
        if (close) {
            windowClose();
        }
        super.getDriver().switchTo().window(listHandles.get(listHandles.size() - 1));
    }

    /**
     * 关闭浏览器
     */
    public void browserOff() {
        super.getDriver().quit();
        log4j.info("···测完了吗就就关闭浏览器了?");
    }

    /**
     * 进frame
     *
     * @param webElement frame元素位置
     */
    public void goFrame(WebElement webElement) {
        super.getDriver().switchTo().frame(webElement);
    }

    /**
     * 退出frame
     */
    public void outFrame() {
        super.getDriver().switchTo().defaultContent();
    }

    /**
     * 最大化窗口
     */
    public void windowMax() {
        super.getDriver().manage().window().maximize();
    }

    /**
     * 确认弹窗
     *
     * @param select false/取消,ture/确定
     */
    public void windowPopUp(boolean select) {
        Alert alert = super.getDriver().switchTo().alert();
//        log4j.info("有警告框弹出内容为:" + alert.getText());
        if (select) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    /**
     * 屏幕截图
     */
    public void screenshot() {
        //相对路径
        File relative = new File("");
        String relativePath = relative.getAbsolutePath() + File.separator + "screenshot\\";
        // 截屏操作 图片已当前时间命名
        //转换时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMdd-HH-mm-ss");
        //获取当前时间
        String time = dateFormat.format(Calendar.getInstance().getTime());
        //执行屏幕截取
        File srcFile = ((TakesScreenshot) super.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(relativePath, time + ".png"));
        } catch (IOException e) {
            log4j.debug("截图失败!");
            System.out.println("");
        }
        log4j.info("截图完成!");
    }

    /**
     * 强制等待
     *
     * @param time 毫秒值
     */
    public void awaitEnforce(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示等待by是否显示和可操作
     *
     * @param by   定位值
     * @param time 分钟
     */
    public void awaitDisplay(By by, int time) {
        /*
        页面元素是否在页面上可用和可被单击	    elementToBeClickable(By locator)
        页面元素处于被选中状态	            elementToBeSelected(WebElement element)
        页面元素在页面中存在	            presenceOfElementLocated(By locator)
        在页面元素中是否包含特定的文本	    textToBePresentInElement(By locator)
        页面元素值　	                    textToBePresentInElementValue(Bylocator locator, String text)
        标题	                            titleContains(String title)
         */
        try {
            new WebDriverWait(super.getDriver(), time).until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e) {
            log4j.debug("···此by定位值等待超时:" + by);
        }
    }

    /**
     * 隐式等待
     */
    public void awaitHide() {
        super.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * 清空cookies
     */
    public void clearCookies() {
        super.getDriver().manage().deleteAllCookies();
    }

}
