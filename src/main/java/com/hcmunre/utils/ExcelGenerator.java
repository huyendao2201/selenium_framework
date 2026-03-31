package com.hcmunre.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {
    public static void main(String[] args) {
        String filePath = "c:\\Study\\HCMUNRE\\Tester\\Tuan9\\Lab9_POM_Project\\src\\test\\resources\\login_data.xlsx";
        try (Workbook workbook = new XSSFWorkbook()) {
            // 1. SmokeCases
            Sheet smoke = workbook.createSheet("SmokeCases");
            Row h1 = smoke.createRow(0);
            String[] heads1 = { "username", "password", "expected_url", "description" };
            for (int i = 0; i < heads1.length; i++)
                h1.createCell(i).setCellValue(heads1[i]);

            addData(smoke, 1, "standard_user", "secret_sauce", "https://www.saucedemo.com/inventory.html",
                    "Đăng nhập thành công user chuẩn");
            addData(smoke, 2, "problem_user", "secret_sauce", "https://www.saucedemo.com/inventory.html",
                    "Đăng nhập thành công user lỗi hình ảnh");
            addData(smoke, 3, "performance_glitch_user", "secret_sauce", "https://www.saucedemo.com/inventory.html",
                    "Đăng nhập thành công user load chậm");

            // 2. NegativeCases
            Sheet negative = workbook.createSheet("NegativeCases");
            Row h2 = negative.createRow(0);
            String[] heads2 = { "username", "password", "expected_error", "description" };
            for (int i = 0; i < heads2.length; i++)
                h2.createCell(i).setCellValue(heads2[i]);

            addData(negative, 1, "standard_user", "wrong_pass",
                    "Epic sadface: Username and password do not match any user in this service", "Sai password");
            addData(negative, 2, "locked_out_user", "secret_sauce",
                    "Epic sadface: Sorry, this user has been locked out.", "Tài khoản bị khóa");
            addData(negative, 3, "", "secret_sauce", "Epic sadface: Username is required", "Để trống username");
            addData(negative, 4, "standard_user", "", "Epic sadface: Password is required", "Để trống password");
            addData(negative, 5, "invalid_user", "invalid_pass",
                    "Epic sadface: Username and password do not match any user in this service", "Sai cả user và pass");

            // 3. BoundaryCases
            Sheet boundary = workbook.createSheet("BoundaryCases");
            Row h3 = boundary.createRow(0);
            String[] heads3 = { "username", "password", "expected_error", "description" };
            for (int i = 0; i < heads3.length; i++)
                h3.createCell(i).setCellValue(heads3[i]);

            addData(boundary, 1, "a".repeat(100), "secret_sauce",
                    "Epic sadface: Username and password do not match any user in this service", "Username quá dài");
            addData(boundary, 2, "standard_user", "a".repeat(100),
                    "Epic sadface: Username and password do not match any user in this service", "Password quá dài");
            addData(boundary, 3, "user!@#", "pass$%^",
                    "Epic sadface: Username and password do not match any user in this service", "Ký tự đặc biệt");
            addData(boundary, 4, "' or 1=1 --", "password",
                    "Epic sadface: Username and password do not match any user in this service",
                    "SQL Injection pattern");

            java.io.File file = new java.io.File(filePath);
            file.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            System.out.println("Excel file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addData(Sheet sheet, int rowIdx, String col1, String col2, String col3, String col4) {
        Row row = sheet.createRow(rowIdx);
        row.createCell(0).setCellValue(col1);
        row.createCell(1).setCellValue(col2);
        row.createCell(2).setCellValue(col3);
        row.createCell(3).setCellValue(col4);
    }
}
