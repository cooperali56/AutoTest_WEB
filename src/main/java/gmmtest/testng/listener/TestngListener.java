package gmmtest.testng.listener;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>>模块测试准备开启<<<");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>>模块测试完成<<<");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("很遗憾测试失败了!!!");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("这块测试跳过了");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("测试失败但在里面有一些成功的难受");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("-----整套Test开动走你！-----");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("-----整套Test结束！-----");
    }

}
