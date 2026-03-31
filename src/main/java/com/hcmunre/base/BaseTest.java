package com.hcmunre.base;

import com.hcmunre.config.ConfigReader;
import com.hcmunre.driver.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser);

        int timeout = ConfigReader.getInstance().getIntProperty("explicit.wait");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout / 2));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        // 🔥 attach screenshot khi fail
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            attachScreenshot();
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Screenshot khi test fail", type = "image/png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}