package com.hcmunre.pages;

import com.hcmunre.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_badge")
    private List<WebElement> cartBadgeList; // Dùng List để tránh throw exception nếu không có badge

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementDisplayed(inventoryList);
    }

    public InventoryPage addFirstItemToCart() {
        WebElement firstItem = inventoryItems.get(0);
        firstItem.findElement(By.tagName("button")).click();
        return this;
    }

    public InventoryPage addItemByName(String name) {
        for (WebElement item : inventoryItems) {
            String itemName = item.findElement(By.className("inventory_item_name")).getText();
            if (itemName.equalsIgnoreCase(name)) {
                item.findElement(By.tagName("button")).click();
                break;
            }
        }
        return this;
    }

    public int getCartItemCount() {
        if (cartBadgeList.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(cartBadgeList.get(0).getText());
    }

    public CartPage goToCart() {
        cartLink.click();
        return new CartPage(driver);
    }
}
