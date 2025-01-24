package gmmtest.driverapi;

import gmmtest.modules.LoginModule;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 实例所需的webdriver
 */
public class BrowserDrivers {

    private WebDriver driver;

    public BrowserDrivers() {
    }

    public BrowserDrivers(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private final Logger log4j = Logger.getLogger(BrowserDrivers.class);

    /**
     * webdriver实例选择
     *
     * @param browser 浏览器名称
     */
    public void browserSelect(String browser) {
        log4j.info(browser + "Driver启动中~");
        switch (browser) {
            case "chrome":
                chromeDriver();
                break;
            case "firefox":
                firefoxDriver();
                break;
            case "edge":
                edgeDriver();
                break;
            case "h5":
                chromeH5Driver();
                break;
            default:
                System.out.println("输入错误GG");
        }
    }

    //实例chrome浏览器
    private void chromeDriver() {
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");
        ChromeOptions optionsC = new ChromeOptions();
        optionsC.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(optionsC);
    }

    //实例firefox浏览器
    private void firefoxDriver() {
        System.setProperty("webdriver.gecko.driver", "webdriver\\geckodriver.exe");
        FirefoxOptions optionsF = new FirefoxOptions();
        optionsF.setAcceptInsecureCerts(true);
        driver = new FirefoxDriver(optionsF);
    }

    //实例edge浏览器
    private void edgeDriver() {
        System.setProperty("webdriver.edge.driver", "webdriver\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.setAcceptInsecureCerts(true);
        driver = new EdgeDriver(options);
    }

    //chromeH5模式
    private void chromeH5Driver() {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone X");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");
        driver = new ChromeDriver(chromeOptions);

        //        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--disable-infobars");
//        //不加载图片
//        /*
//        Map<String, Object> prefs = new HashMap<String, Object>();
//        prefs.put("profile.managed_default_content_settings.images", 2);
//        options.setExperimentalOption("prefs", prefs);
//        */
//        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
//        deviceMetrics.put("width", 414);
//        deviceMetrics.put("height", 736);
//        deviceMetrics.put("pixelRatio", 3.0);
//        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
//        mobileEmulation.put("deviceMetrics", deviceMetrics);
//        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
//                "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
//        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
//        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");

    }
}
