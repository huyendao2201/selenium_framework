package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.CartPage;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void directToInventory() {
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    public void testAddItemsToCart() {
        // Fluent Interface check: login().addFirst().addItemByName()
        inventoryPage.addFirstItemToCart()
                .addItemByName("Sauce Labs Bike Light");

        Assert.assertEquals(inventoryPage.getCartItemCount(), 2, "Cart badge should show 2 items");
    }

    @Test
    public void testRemoveItemFromCart() {
        CartPage cartPage = inventoryPage.addFirstItemToCart()
                .goToCart();

        int initialCount = cartPage.getItemCount();
        cartPage.removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), initialCount - 1, "Item count should decrease by 1");
    }

    @Test
    public void testEmptyCartItemCount() {
        CartPage cartPage = inventoryPage.goToCart();
        // Check case 0 items, should not throw exception
        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart should be empty initially");
    }
}
