package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.CartPage;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import com.hcmunre.config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RefactoredClassicTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        driver.get(ConfigReader.getInstance().getProperty("url"));
        loginPage = new LoginPage(driver);
    }

    // --- REFACTORED FROM LAB 1 (LOGIN) ---

    @Test(description = "Refactored: Đăng nhập thành công với tài khoản hợp lệ")
    public void testLoginSuccess() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "URL phai chua /inventory.html");
        Assert.assertTrue(inventoryPage.isLoaded(), "Trang Inventory phai hien thi");
    }

    @Test(description = "Refactored: Đăng nhập sai mật khẩu")
    public void testLoginWrongPassword() {
        loginPage.loginExpectingFailure("standard_user", "wrong_password_123");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Thong bao loi phai hien thi");
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty(), "Noi dung loi khong duoc rong");
    }

    @Test(description = "Refactored: Bỏ trống username")
    public void testLoginEmptyUsername() {
        loginPage.loginExpectingFailure("", "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Loi phai chua 'Username is required'");
    }

    // --- REFACTORED FROM LAB 2 (CART) ---

    @Test(description = "Refactored: Thêm sản phẩm vào giỏ hàng - kiểm tra badge")
    public void testAddItemToCart() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Badge phai hien thi 1");
    }

    @Test(description = "Refactored: Xóa sản phẩm khỏi giỏ hàng")
    public void testRemoveItemFromCart() {
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart();

        cartPage.removeFirstItem();
        Assert.assertEquals(cartPage.getItemCount(), 0, "Gio hang phai trong sau khi xoa");
    }

    @Test(description = "Refactored: Nút Continue Shopping")
    public void testContinueShopping() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce")
                .goToCart()
                .continueShopping();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "Phai quay lai trang Inventory");
    }
}
