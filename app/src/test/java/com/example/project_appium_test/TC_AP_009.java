package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TC_AP_009 extends BaseTest {

    public void selectDate(String dateDesc) throws InterruptedException {
        while (true) {
            try {
                driver.findElement(By.xpath("//android.view.View[@content-desc='" + dateDesc + "']")).click();
                test.info("เลือกวันที่ " + dateDesc + " สำเร็จ");
                break;
            } catch (Exception e) {
                driver.findElement(By.id("android:id/prev")).click();
                Thread.sleep(500);
            }
        }
    }

    @Test
    public void TestCase9() throws InterruptedException {
        login("testapp", "123456789");
        allowNotificationIfVisible();

        driver.findElement(By.id("com.example.project_news_app:id/search_button")).click();
        test.info("เข้าสู่หน้าการค้นหาข่าว");
        Thread.sleep(2000);

        String startDate = "18/08/2024";
        String endDate = "29/07/2025";
        String startDateDesc = "18 August 2024";
        String endDateDesc = "29 July 2025";

        Thread.sleep(1000);
        driver.findElement(By.id("com.example.project_news_app:id/start_date_edit_text")).click();
        selectDate(startDateDesc);
        driver.findElement(By.id("android:id/button1")).click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.example.project_news_app:id/end_date_edit_text")).click();
        selectDate(endDateDesc);
        driver.findElement(By.id("android:id/button1")).click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.example.project_news_app:id/search_by_date_range_button")).click();
        test.info("กดค้นหาข่าวตามช่วงวันที่ " + startDate + " ถึง " + endDate);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> dateElements = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.id("com.example.project_news_app:id/date_added")
                )
        );

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int matchedCount = 0;
        for (WebElement elem : dateElements) {
            String dateText = elem.getText().trim(); // เช่น "25/08/2024 20:05"
            String[] parts = dateText.split(" ");
            if (parts.length > 0) {
                try {
                    LocalDate itemDate = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (!itemDate.isBefore(start) && !itemDate.isAfter(end)) {
                        matchedCount++;
                    } else {
                        test.warning("ข่าวอยู่นอกช่วงวันที่: " + dateText);
                    }
                } catch (DateTimeParseException e) {
                    test.warning("รูปแบบวันที่ไม่ถูกต้อง: " + dateText);
                }
            }
        }
        test.pass("พบข่าวในช่วงวันที่ที่กำหนดทั้งหมด: " + matchedCount + " รายการ");
        Assert.assertEquals(matchedCount, dateElements.size(), "มีข่าวบางรายการที่ไม่อยู่ในช่วงวันที่ที่เลือกไว้");
    }

}



