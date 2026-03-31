package com.hcmunre.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        String env = System.getProperty("env", "dev");
        String fileName = "config-" + env + ".properties";
        String filePath = "src/test/resources/" + fileName;

        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            System.out.println("Đang dùng môi trường: " + env);
        } catch (IOException e) {
            throw new RuntimeException("Không tìm thấy file config cho môi trường: " + env, e);
        }
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public String getUsername() {
        String username = System.getenv("APP_USERNAME");
        if (username == null || username.isBlank()) {
            username = getProperty("app.username");
        }
        return username;
    }

    public String getPassword() {
        String password = System.getenv("APP_PASSWORD");
        if (password == null || password.isBlank()) {
            password = getProperty("app.password");
        }
        return password;
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }
}