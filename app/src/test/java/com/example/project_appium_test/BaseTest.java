package com.example.project_appium_test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BaseTest {
    protected AndroidDriver driver;
    protected static ExtentReports extent = ExtentManager.getInstance();
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpExtent() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        test = extent.createTest(method.getName());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "14.0");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "com.example.project_news_app");
        capabilities.setCapability("appActivity", "com.example.project_news_app.LoginActivity");
        capabilities.setCapability("app", "D:\\Android Studio\\app-debug.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
        test.info("App opened successfully");
    }

    public void login(String username, String password) {
        driver.findElementById("com.example.project_news_app:id/username").sendKeys(username);
        driver.findElementById("com.example.project_news_app:id/password").sendKeys(password);
        driver.findElementById("com.example.project_news_app:id/login_button").click();
        test.info("Login success");
    }

    public void allowNotificationIfVisible() throws InterruptedException {
        Thread.sleep(2000);
        List<MobileElement> allowButtons = driver.findElementsById("com.android.permissioncontroller:id/permission_allow_button");
        if (!allowButtons.isEmpty()) {
            allowButtons.get(0).click();
            test.info("Notification allowed");
        } else {
            test.warning("No notification permission appeared");
        }
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        } else {
            test.skip("Test Skipped");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        System.out.println("Flushing Extent...");
        extent.flush();
    }
}

