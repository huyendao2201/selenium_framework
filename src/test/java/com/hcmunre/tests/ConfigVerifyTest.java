package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.LoginPage;
import com.hcmunre.config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigVerifyTest extends BaseTest {

    @Test
    public void testConfigEnvironment() {
        String url = ConfigReader.getInstance().getProperty("url");
        int timeout = ConfigReader.getInstance().getIntProperty("explicit.wait");

        System.out.println("--- KIỂM TRA CẤU HÌNH ---");
        System.out.println("URL từ Config: " + url);
        System.out.println("Timeout từ Config: " + timeout);

        driver.get(url);
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"), "URL không đúng môi trường");
    }
}
