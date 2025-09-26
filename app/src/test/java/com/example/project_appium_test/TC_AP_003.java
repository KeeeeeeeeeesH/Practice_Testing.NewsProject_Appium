package com.example.project_appium_test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import io.appium.java_client.MobileElement;

public class TC_AP_003 extends BaseTest {

    @Test
    public void TestCase3() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();

        driver.findElementById("com.example.project_news_app:id/navigation_profile").click();
        test.info("เข้าสู่หน้าโปรไฟล์");

        Thread.sleep(2000);
        driver.findElementById("com.example.project_news_app:id/readLaterButton").click();
        test.info("เปิดหน้ารายการข่าวอ่านภายหลัง");

        Thread.sleep(2000);
        List<MobileElement> newsItems = driver.findElementsById("com.example.project_news_app:id/news_item_layout");
        Assert.assertTrue(newsItems.size() > 0, "ไม่พบข่าวในรายการอ่านภายหลัง");
        test.pass("พบ " + newsItems.size() + " รายการข่าวอ่านภายหลัง");
        Thread.sleep(2000);
    }
}
