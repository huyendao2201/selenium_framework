package com.hcmunre.pages;

import com.hcmunre.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Lấy số lượng sản phẩm trong giỏ hàng")
    public int getItemCount() {
        return cartItems.size();
    }

    @Step("Xóa sản phẩm đầu tiên trong giỏ")
    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
        return this;
    }

    @Step("Click Checkout")
    public void goToCheckout() {
        checkoutButton.click();
    }

    @Step("Tiếp tục mua hàng")
    public InventoryPage continueShopping() {
        continueShoppingButton.click();
        return new InventoryPage(driver);
    }

    @Step("Lấy danh sách tên sản phẩm")
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement name : itemNames) {
            names.add(name.getText());
        }
        return names;
    }
}