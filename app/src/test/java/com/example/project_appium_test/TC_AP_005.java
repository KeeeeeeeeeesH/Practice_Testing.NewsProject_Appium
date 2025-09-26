package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

public class TC_AP_005 extends BaseTest {

    @Test
    public void TestCase5() throws InterruptedException {
        driver.findElementById("com.example.project_news_app:id/register").click();
        test.info("เข้าสู่หน้าสมัครสมาชิก");

        waitForElement(By.id("com.example.project_news_app:id/first_name"));

        fillRegisterForm("FNregister", "LNregister", "0974000002",
                "MAILregister@mail.com", "UserTestRegis2", "PassTestRegis",
                "PassTestRegis");

        test.info("กรอกข้อมูลในแบบฟอร์มสมัครสมาชิกด้วยข้อมูลที่ซ้ำกับในระบบ");

        submitAndAssertToast("ชื่อผู้ใช้งาน, อีเมล์, หรือหมายเลขโทรศัพท์นี้ มีอยู่ในระบบแล้ว");
    }

    public void fillRegisterForm(String Fname, String Lname, String phoneNum, String Email,
                                 String Username, String Password, String confirmPassword) {
        driver.findElement(By.id("com.example.project_news_app:id/first_name")).sendKeys(Fname);
        driver.findElement(By.id("com.example.project_news_app:id/last_name")).sendKeys(Lname);
        driver.findElement(By.id("com.example.project_news_app:id/phone_number")).sendKeys(phoneNum);
        driver.findElement(By.id("com.example.project_news_app:id/email")).sendKeys(Email);
        driver.findElement(By.id("com.example.project_news_app:id/username")).sendKeys(Username);
        driver.findElement(By.id("com.example.project_news_app:id/password")).sendKeys(Password);
        driver.findElement(By.id("com.example.project_news_app:id/confirm_password")).sendKeys(confirmPassword);
    }

    public void submitAndAssertToast(String expectedText) {
        driver.findElement(By.id("com.example.project_news_app:id/register_button")).click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            MobileElement toastElement = (MobileElement) wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Toast[1]"))
            );

            String actualToastMessage = toastElement.getAttribute("name");

            if (actualToastMessage.equals(expectedText)) {
                test.pass("ข้อความแจ้งเตือนถูกต้อง: " + actualToastMessage);
            } else {
                test.fail("ข้อความแจ้งเตือนไม่ตรง \nExpected: " + expectedText + "\nActual: " + actualToastMessage);
            }

            Assert.assertEquals(actualToastMessage, expectedText, "ข้อความแจ้งเตือนไม่ถูกต้อง");

        } catch (NoSuchElementException e) {
            test.fail("ไม่พบ Toast แจ้งเตือนภายในเวลาที่กำหนด");
            Assert.fail("Toast ไม่ปรากฏ");
        }
    }

    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}