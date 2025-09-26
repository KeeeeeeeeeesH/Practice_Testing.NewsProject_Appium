package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import io.appium.java_client.MobileElement;

public class TC_AP_008 extends BaseTest {

    @Test
    public void TestCase8() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();

        driver.findElement(By.id("com.example.project_news_app:id/search_button")).click();
        test.info("เข้าสู่หน้าการค้นหาข่าว");
        Thread.sleep(2000);

        driver.findElement(By.id("com.example.project_news_app:id/search_by_date_edit_text")).click();
        Thread.sleep(1000);

        while (true) {
            try {
                driver.findElement(By.xpath("//android.view.View[@content-desc='25 August 2024']")).click();
                test.info("เลือกวันที่ 25 August 2024 สำเร็จ");
                break;
            } catch (Exception e) {
                driver.findElement(By.id("android:id/prev")).click();
                Thread.sleep(500);
            }
        }

        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(By.id("com.example.project_news_app:id/search_by_date_button")).click();
        test.info("กดค้นหาข่าวตามวันที่สำเร็จ");

        String expectedDate = "25/08/2024";
        int matchedCount = 0;

        Thread.sleep(2000);
        List<MobileElement> dateElements = driver.findElements(By.id("com.example.project_news_app:id/date_added"));

        for (MobileElement dateElem : dateElements) {
            String dateText = dateElem.getText();
            if (dateText.contains(expectedDate)) {
                matchedCount++;
            } else {
                test.warning("พบข่าวที่ไม่ตรงวันที่: " + dateText);
            }
        }

        test.pass("พบข่าวตรงวันที่ " + expectedDate + " ทั้งหมด " + matchedCount + " รายการ");
        Assert.assertEquals(matchedCount, dateElements.size(), "มีข่าวบางรายการที่ไม่ตรงกับวันที่ที่เลือกไว้");
    }
}
