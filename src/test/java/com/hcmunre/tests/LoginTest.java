package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLogin() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginSuccess() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded after successful login");
    }

    @Test
    public void testLoginLockedOutUser() {
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for locked out user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"),
                "Wrong error message");
    }

    @Test
    public void testLoginInvalidCredentials() {
        loginPage.loginExpectingFailure("wrong_user", "wrong_pass");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed for invalid user");
    }
}
