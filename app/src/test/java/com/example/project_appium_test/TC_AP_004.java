package com.example.project_appium_test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

public class TC_AP_004 extends BaseTest {

    @Test
    public void TestCase4() throws InterruptedException {
        driver.findElementById("com.example.project_news_app:id/register").click();
        test.info("เข้าสู่หน้าสมัครสมาชิก");

        waitForElement(By.id("com.example.project_news_app:id/first_name"));

        fillRegisterForm("FNregister", "LNregister", "0974000001",
                "MAILregister@mail.com", "UserTestRegis", "PassTestRegis",
                "PassTestRegis");

        test.info("กรอกข้อมูลในแบบฟอร์มสมัครสมาชิกเรียบร้อย");

        submitAndAssertAlert("สมัครสมาชิกสำเร็จ");

        Thread.sleep(2000);
        String currentActivity = driver.currentActivity();

        try {
            Assert.assertEquals(currentActivity, ".LoginActivity", "ไม่ได้อยู่ในหน้าล็อคอิน");
            test.pass("กลับมาหน้าล็อคอินสำเร็จ: " + currentActivity);
        } catch (AssertionError e) {
            test.fail("ไม่ได้กลับมาหน้าล็อคอิน: currentActivity = " + currentActivity);
            throw e;
        }

        Thread.sleep(2000);
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

    public void submitAndAssertAlert(String expectedText) throws InterruptedException {
        driver.findElement(By.id("com.example.project_news_app:id/register_button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        MobileElement alertMessage = (MobileElement) wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("android:id/message"))
        );

        String alertText = alertMessage.getText();

        try {
            Assert.assertEquals(alertText, expectedText, "ข้อความแจ้งเตือนไม่ถูกต้อง");
            test.pass("ข้อความแจ้งเตือนตรงตามที่คาดไว้: " + alertText);
        } catch (AssertionError e) {
            test.fail("ข้อความแจ้งเตือนไม่ตรง: คาดว่า \"" + expectedText + "\", แต่ได้ \"" + alertText + "\"");
            throw e;
        }

        driver.findElement(By.id("android:id/button1")).click();
        test.info("ปิดกล่องแจ้งเตือน");
    }

    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
