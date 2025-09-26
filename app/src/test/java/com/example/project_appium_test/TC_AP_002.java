package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import io.appium.java_client.MobileElement;

public class TC_AP_002 extends BaseTest{

    @Test
    public void TestCase2() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();
        selectCategory("เศรษฐกิจ");
        checkNewsDisplayedInCategory();
    }

    public void selectCategory(String categoryName) throws InterruptedException {
        Thread.sleep(2000);
        try{
            String xpath =  "//android.widget.TextView[@resource-id='com.example.project_news_app:id/category_name' and @text='" + categoryName + "']";
            WebElement category = driver.findElement(By.xpath(xpath));
            category.click();
            test.info(" หมวดหมู่ที่เลือก : " + categoryName);
        } catch (Exception e) {
            test.fail("ไม่พบหมวดหมู่ข่าว : " + categoryName);
        }
    }

    public void checkNewsDisplayedInCategory() throws InterruptedException {
        Thread.sleep(2000);
        List<MobileElement> newsItems = driver.findElementsById("com.example.project_news_app:id/news_item_layout");
        Assert.assertTrue(newsItems.size() > 0, "ไม่มีข่าวในหมวดหมู่ที่เลือก");
        test.pass("เจอ " + newsItems.size() + " รายการข่าวในหมวดหมู่นี้");
    }
}