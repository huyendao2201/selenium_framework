package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.CartPage;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import com.hcmunre.utils.TestDataFactory;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CheckoutFakerTest extends BaseTest {

    @Test
    public void testCheckoutWithRandomData() {
        // 1. Đăng nhập và vào giỏ hàng
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        CartPage cartPage = inventoryPage.addFirstItemToCart().goToCart();
        cartPage.goToCheckout();

        // 2. Sinh dữ liệu ngẫu nhiên từ Factory
        Map<String, String> customer = TestDataFactory.randomCheckoutData();

        System.out.println("--- ĐANG CHẠY TEST VỚI DỮ LIỆU FAKER ---");
        System.out.println("First Name: " + customer.get("firstName"));
        System.out.println("Last Name: " + customer.get("lastName"));
        System.out.println("Postal Code: " + customer.get("postalCode"));
        System.out.println("---------------------------------------");

        // 3. Điền form Checkout (Dùng trực tiếp driver vì bài 2 chỉ yêu cầu 3 Page đầu)
        driver.findElement(By.id("first-name")).sendKeys(customer.get("firstName"));
        driver.findElement(By.id("last-name")).sendKeys(customer.get("lastName"));
        driver.findElement(By.id("postal-code")).sendKeys(customer.get("postalCode"));
        driver.findElement(By.id("continue")).click();

        // 4. Kiểm tra xem đã sang trang Overview chưa
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Không sang được trang Checkout Overview");
    }
}
