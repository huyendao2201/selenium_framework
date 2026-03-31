package com.hcmunre.pages;

import com.hcmunre.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Đăng nhập thành công -> Trả về InventoryPage
     */
    public InventoryPage login(String user, String pass) {
        waitAndType(user, pass);
        loginButton.click();
        return new InventoryPage(driver);
    }

    /**
     * Đăng nhập thất bại -> Ở lại LoginPage
     */
    public LoginPage loginExpectingFailure(String user, String pass) {
        waitAndType(user, pass);
        loginButton.click();
        return this;
    }

    private void waitAndType(String user, String pass) {
        usernameField.clear();
        usernameField.sendKeys(user);
        passwordField.clear();
        passwordField.sendKeys(pass);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}
