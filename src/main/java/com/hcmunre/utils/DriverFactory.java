package com.hcmunre.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        // Detect CI environment
        boolean isCI = System.getenv("CI") != null;

        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI);
            default -> createChromeDriver(isCI);
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new"); // Chrome 112+
            options.addArguments("--no-sandbox"); // BẮT BUỘC trên CI
            options.addArguments("--disable-dev-shm-usage"); // tránh crash RAM
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        WebDriverManager.chromedriver()
                .clearDriverCache()
                .clearResolutionCache()
                .setup();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
        }

        WebDriverManager.firefoxdriver()
                .clearDriverCache()
                .clearResolutionCache()
                .setup();
        return new FirefoxDriver(options);
    }
}