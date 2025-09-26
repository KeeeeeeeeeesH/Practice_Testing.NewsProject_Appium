package com.example.project_appium_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import io.appium.java_client.MobileElement;

public class TC_AP_001 extends BaseTest {

    @Test
    public void TestCase1() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();
        checkNewsDisplayed();
    }

    public void checkNewsDisplayed() throws InterruptedException {
        Thread.sleep(2000);
        List<MobileElement> newsItem = driver.findElementsById("com.example.project_news_app:id/news_item_layout");
        test.info("เข้าสู่หน้า Home และกำลังตรวจสอบรายการข่าวที่แสดงผล");
        Assert.assertTrue(newsItem.size() > 0, " ไม่พบรายการข่าวในหน้าแรก");
        test.pass("เจอ" + newsItem.size() + " รายการข่าว");
        Thread.sleep(2000);
    }
}