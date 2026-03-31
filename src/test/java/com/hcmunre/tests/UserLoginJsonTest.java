package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.models.UserData;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import com.hcmunre.utils.JsonReader;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class UserLoginJsonTest extends BaseTest {
    private String testName = "";
    private final String jsonPath = "src/test/resources/testdata/users.json";

    @BeforeMethod
    public void setupTest(Method method, Object[] testData) {
        if (testData != null && testData.length > 0 && testData[0] instanceof UserData) {
            this.testName = ((UserData) testData[0]).getDescription();
        } else {
            this.testName = method.getName();
        }
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "UserDataJson")
    public Object[][] getUserData() {
        List<UserData> users = JsonReader.readUsers(jsonPath);
        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }

    @Test(dataProvider = "UserDataJson")
    public void testLoginWithJson(UserData user) {
        LoginPage loginPage = new LoginPage(driver);

        if (user.isExpectSuccess()) {
            InventoryPage inventoryPage = loginPage.login(user.getUsername(), user.getPassword());
            Assert.assertTrue(inventoryPage.isLoaded(), "Đăng nhập thất bại cho: " + user.getDescription());
        } else {
            loginPage.loginExpectingFailure(user.getUsername(), user.getPassword());
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiển thị cho: " + user.getDescription());
        }
    }
}
