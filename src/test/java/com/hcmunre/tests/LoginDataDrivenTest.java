package com.hcmunre.tests;

import com.hcmunre.base.BaseTest;
import com.hcmunre.pages.InventoryPage;
import com.hcmunre.pages.LoginPage;
import com.hcmunre.utils.ExcelReader;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class LoginDataDrivenTest extends BaseTest {

    private String testName = "";
    private final String excelPath = "src/test/resources/testdata/login_data.xlsx";

    @BeforeMethod
    public void setupTest(Method method, Object[] testData) {
        // Lấy description từ cột cuối cùng để đặt tên cho Test case
        if (testData != null && testData.length > 0) {
            this.testName = testData[testData.length - 1].toString();
        } else {
            this.testName = method.getName();
        }
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "SmokeData")
    public Object[][] getSmokeData() {
        ExcelReader reader = new ExcelReader(excelPath);
        return reader.getSheetData("SmokeCases");
    }

    @DataProvider(name = "NegativeData")
    public Object[][] getNegativeData() {
        ExcelReader reader = new ExcelReader(excelPath);
        return reader.getSheetData("NegativeCases");
    }

    @DataProvider(name = "BoundaryData")
    public Object[][] getBoundaryData() {
        ExcelReader reader = new ExcelReader(excelPath);
        return reader.getSheetData("BoundaryCases");
    }

    @Test(dataProvider = "SmokeData", groups = "smoke")
    public void testLoginSmoke(String user, String pass, String expectedUrl, String desc) {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.login(user, pass);

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "URL không khớp cho case: " + desc);
        Assert.assertTrue(inventoryPage.isLoaded(), "Trang Inventory không load cho case: " + desc);
    }

    @Test(dataProvider = "NegativeData", groups = "regression")
    public void testLoginNegative(String user, String pass, String expectedError, String desc) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure(user, pass);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiển thị cho case: " + desc);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError, "Nội dung lỗi không khớp cho case: " + desc);
    }

    @Test(dataProvider = "BoundaryData", groups = "regression")
    public void testLoginBoundary(String user, String pass, String expectedError, String desc) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure(user, pass);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiển thị cho case: " + desc);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedError, "Nội dung lỗi không khớp cho case: " + desc);
    }
}
