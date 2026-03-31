package com.hcmunre.pages;

import com.hcmunre.base.BasePage;
import io.qameta.allure.Step;
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

    @Step("Đăng nhập với username: {user}")
    public InventoryPage login(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
        return new InventoryPage(driver);
    }

    @Step("Đăng nhập thất bại với username: {user}")
    public LoginPage loginExpectingFailure(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
        return this;
    }

    @Step("Nhập username")
    public void enterUsername(String user) {
        usernameField.clear();
        usernameField.sendKeys(user);
    }

    @Step("Nhập password")
    public void enterPassword(String pass) {
        passwordField.clear();
        passwordField.sendKeys(pass);
    }

    @Step("Click nút Login")
    public void clickLogin() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}