# AutoTest_WEB

![Selenium](https://img.shields.io/badge/Selenium-4.0.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.0.0-blue)
![Java](https://img.shields.io/badge/Java-1.7-orange)

基于Java+Selenium+TestNG构建的Web UI自动化测试框架，采用Page Object模式实现。

## 简介

这是一个面向Web应用的UI自动化测试框架，使用Page Object模式分离页面与业务逻辑，提高代码复用性和可维护性。

## 特性

- 基于Page Object模式设计
- 多浏览器兼容测试
- 集成测试报告(ReportNG, ExtentReports)
- 数据驱动支持
- 丰富的元素定位与操作机制
- 完善的测试监听器

## 技术栈

- Java 1.7+
- Selenium WebDriver 4.0
- TestNG 7.0
- Maven
- Log4j
- Apache POI (Excel处理)
- JSON

## 目录结构

```
AutoTest_WEB/
├─ src/
│  ├─ main/java/gmmtest/        # 主代码
│  │  ├─ driverapi/             # 浏览器驱动封装
│  │  ├─ page/                  # 页面对象
│  │  ├─ modules/               # 业务模块
│  │  ├─ testng/                # 测试框架
│  │  └─ tools/                 # 工具类
│  └─ test/java/                # 测试代码
│     ├─ flowxml/               # 测试流程
│     └─ testclass/             # 测试类
├─ testcase/                    # 测试用例
├─ webdriver/                   # 浏览器驱动
├─ pom.xml                      # 项目配置
└─ testng.xml                   # 测试配置
```

## 快速开始

### 前置条件

- JDK 1.7+
- Maven 3.0+
- 浏览器驱动(Chrome/Firefox/Edge)

### 配置

1. 克隆仓库
```bash
git clone https://github.com/yourusername/AutoTest_WEB.git
```

2. 安装依赖
```bash
mvn clean install
```

3. 添加浏览器驱动到`webdriver`目录

### 运行测试

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### 测试报告

执行后在`test-output`目录查看HTML和XML格式的测试报告。

## 使用示例

```java
@Test
public void loginTest() {
    Login login = new Login();
    login.loginAction("username", "password");
    Assert.assertTrue(login.isLoginSuccess());
}
```

## 许可证

[MIT](LICENSE)
