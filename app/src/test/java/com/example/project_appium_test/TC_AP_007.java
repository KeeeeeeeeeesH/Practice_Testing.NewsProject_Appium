package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import io.appium.java_client.MobileElement;

public class TC_AP_007 extends BaseTest {

    @Test
    public void TestCase7() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();

        driver.findElement(By.id("com.example.project_news_app:id/search_button")).click();
        test.info("เข้าสู่หน้าการค้นหาข่าว");

        Thread.sleep(2000);

        String keyword = "เครื่องดื่ม";
        driver.findElement(By.id("com.example.project_news_app:id/search_by_name_edit_text")).sendKeys(keyword);
        driver.findElement(By.id("com.example.project_news_app:id/search_by_name_button")).click();
        test.info("ค้นหาด้วยคำว่า: \"" + keyword + "\"");

        Thread.sleep(2000);

        List<MobileElement> newsItems = driver.findElements(By.id("com.example.project_news_app:id/news_item_layout"));
        int matchedCount = 0;

        for (MobileElement item : newsItems) {
            String newsTitle = item.findElement(By.id("com.example.project_news_app:id/news_name")).getText();
            if (newsTitle.contains(keyword)) {
                matchedCount++;
            } else {
                test.warning("ข่าวที่ไม่ตรงกับ keyword: " + newsTitle);
            }
        }

        test.info("ตรวจสอบ keyword \"" + keyword + "\" พบทั้งหมด " + matchedCount + " รายการ");

        try {
            Assert.assertEquals(matchedCount, newsItems.size(), "มีข่าวบางรายการที่ไม่พบ keyword '" + keyword + "'");
            test.pass("แสดงเฉพาะข่าวที่ตรงกับ keyword \"" + keyword + "\" ครบ");
        } catch (AssertionError e) {
            test.fail("พบข่าวที่ไม่ตรง keyword \"" + keyword + "\"");
            throw e;
        }

        Thread.sleep(2000);
    }
}
