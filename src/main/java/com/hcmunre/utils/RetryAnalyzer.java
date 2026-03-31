package com.hcmunre.utils;

import com.hcmunre.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private int maxRetry;

    public RetryAnalyzer() {
        try {
            this.maxRetry = ConfigReader.getInstance().getIntProperty("retry.count");
        } catch (Exception e) {
            this.maxRetry = 0;
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < maxRetry) {
                count++;
                System.out.println("[Retry] Đang thử lại lần thứ: " + count + " cho test: " + result.getName());
                return true;
            }
        }
        return false;
    }
}
