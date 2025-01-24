package gmmtest.page;

import gmmtest.driverapi.DriverElement;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

/**
 * 页面基类
 * 元素操作+窗口操作
 */
public abstract class BasePage extends DriverElement {

    //每个页面都会从excel里面获取定位值集合
    protected abstract void getExcelByData();

    /**
     * 短信检测验证
     */
    public void sms() {
        awaitEnforce(1500);
        WebElement element = getElement(By.xpath("//*[text()='发送验证码']"));
        if (element != null && element.isEnabled() && element.isDisplayed()) {
            elementClick(element);
            elementInput(getElement(By.xpath("//input[contains(@placeholder,'输入短信')]")), "201961");
            awaitEnforce(500);//*[text()='发送验证码']/../..//*[text()='确定']
            getElement(By.xpath("//*[@id='modal-buy-code']/div/div/div[2]/ul/li[3]/button[1]")).click();
        }
        awaitEnforce(1500);
    }

    /**
     * 从excel定位值集合返回指定element
     *
     * @param elementData
     * @param elementName
     * @return
     */
    public WebElement returnExcelElement(String elementData, String elementName) {
        //将从excel拿到的元素定位集合转成功json格式
        JSONObject jsonObject = new JSONObject(elementData);
        //获取指定元素的定位方式和定位值
        JSONObject bys = (JSONObject) jsonObject.get(elementName);
        //转成hashmap
        HashMap hashMap = (HashMap) bys.toMap();
        List<String> byData = new ArrayList<>();
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            byData.add(0, (String) entry.getKey());
            byData.add(1, (String) entry.getValue());
        }
        return getElement(byFilter(byData.get(0), byData.get(1)));
    }

    /**
     * 从excel返回by定位值
     *
     * @param elementData
     * @param elementName
     * @return
     */
    public By returnExcelBy(String elementData, String elementName) {
        //将从excel拿到的元素定位集合转成功json格式
        JSONObject jsonObject = new JSONObject(elementData);
        //获取指定元素的定位方式和定位值
        JSONObject bys = (JSONObject) jsonObject.get(elementName);
        //转成hashmap
        HashMap hashMap = (HashMap) bys.toMap();
        List<String> byData = new ArrayList<>();
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            byData.add(0, (String) entry.getKey());
            byData.add(1, (String) entry.getValue());
        }
        return byFilter(byData.get(0), byData.get(1));
    }
}