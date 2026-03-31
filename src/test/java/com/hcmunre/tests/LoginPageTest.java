package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.base.BasePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void testLoginWithBase() {
        // Truy cập trang lab
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Khởi tạo BasePage để sử dụng các hàm common
        BasePage basePage = new BasePage(driver);

        // Chờ trang load xong
        basePage.waitForPageLoad();

        // Định nghĩa locators
        By usernameField = By.name("username");
        By passwordField = By.name("password");
        By loginButton = By.xpath("//button[@type='submit']");
        By dashboardHeader = By.xpath("//h6");

        // Thực hiện các action qua BasePage
        basePage.waitAndType(usernameField, "Admin");
        basePage.waitAndType(passwordField, "admin123");
        basePage.waitAndClick(loginButton);

        // Kiểm tra kết quả
        basePage.waitForPageLoad();
        boolean isDashboardVisible = basePage.isElementVisible(dashboardHeader);
        Assert.assertTrue(isDashboardVisible, "Dashboard should be visible after login");

        String headerText = basePage.getText(dashboardHeader);
        Assert.assertEquals(headerText, "Dashboard");
    }
}
