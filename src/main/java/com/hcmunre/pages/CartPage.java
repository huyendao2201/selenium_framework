package com.hcmunre.pages;

import com.hcmunre.base.BasePage;

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

    public int getItemCount() {
        return cartItems.size();
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
        return this;
    }

    public void goToCheckout() {
        // Lab yêu cầu trả về CheckoutPage nếu có, nhưng ở đây đề bài chỉ yêu cầu tạo 3
        // Page Objects
        // Nên tôi sẽ giữ void hoặc trả về trang hiện tại tùy ý.
        // Theo chuẩn Lab, ta giả định có thể trả về CheckoutPage hoặc thực hiện action.
        checkoutButton.click();
    }

    public InventoryPage continueShopping() {
        continueShoppingButton.click();
        return new InventoryPage(driver);
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement name : itemNames) {
            names.add(name.getText());
        }
        return names;
    }
}
